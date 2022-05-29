package com.gmy.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.ArticleTagDao;
import com.gmy.blog.dao.CategoryDao;
import com.gmy.blog.dao.TagDao;
import com.gmy.blog.dto.ArticleBackDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.entity.ArticleTagEntity;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.service.ArticleService;
import com.gmy.blog.service.ArticleTagService;
import com.gmy.blog.service.CategoryService;
import com.gmy.blog.service.TagService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmy.blog.enums.ArticleStatusEnum.DRAFT;

/**
 * @author gmydl
 * @title: ArticleService
 * @projectName blog-api
 * @description: 文章的service
 * @date 2022/5/2421:46
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public void publish(ArticleVo articleVo) {
        // 获取文章的分类
        CategoryEntity category = this.saveCategory(articleVo);
        // 保存或者修改文章
        // TODO: 校验数据
        ArticleEntity articleEntity = BeanCopyUtils.copyObject(articleVo, ArticleEntity.class);
        if (Objects.nonNull(category)){
            articleEntity.setCategoryId(category.getId());
        }
        // TODO 设置文章作者
        articleEntity.setUserId(528);
        this.saveOrUpdate(articleEntity);
        // 保存文章的新标签
        this.saveArticleTag(articleVo, articleEntity.getId());
    }

    @Override
    public PageResult<ArticleBackDTO> getAllArticles(ConditionVO condition) {
        // 查文章总量
        Integer count = articleDao.getCountArticle(condition);
        if (count == 0){
            return new PageResult<>();
        }

        // 查询后台文章
        List<ArticleBackDTO> articleBackDTOList = articleDao
                .getListArticle(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // TODO：文章的点赞数量和收藏数量
        articleBackDTOList.forEach( item ->{
            item.setLikeCount(0);
            item.setViewsCount(0);
        });

        return new PageResult<>(articleBackDTOList, count);
    }

    /**
     * 保存文章分类
     * @param articleVo 发布文章时提交的数据
     */
    private CategoryEntity saveCategory(ArticleVo articleVo) {
        // 获取分类名称
        String categoryName = articleVo.getCategoryName();
        CategoryEntity category = categoryDao.selectOne(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getCategoryName, categoryName));
        // 通过分类名 判断实体是否不存在，并且 文章状态不为草稿
        if (Objects.isNull(category) && !articleVo.getStatus().equals(DRAFT.getStatus())){
            category = CategoryEntity.builder()
                    .categoryName(categoryName).build();
            categoryDao.insert(category);
        }
        return category;
    }

    /**
     * 保存创建文章时，新添加的文章标签
     * @param articleVo 获取标签
     * @param articleId 文章 Id
     */
    private void saveArticleTag(ArticleVo articleVo, Integer articleId) {
        // TODO: 编辑文章时的情况
        // 编辑文章则删除文章所有标签
//        if (Objects.nonNull(articleVo.getId())) {
//            articleTagDao.delete(new LambdaQueryWrapper<ArticleTagEntity>()
//                    .eq(ArticleTagEntity::getArticleId, articleVo.getId()));
//        }

        // 获取发布文章时选择的标签， 其中包含用户自己添加的
        List<String> tagNameList = articleVo.getTagNameList();
        // 添加文章标签
        if (CollectionUtil.isNotEmpty(tagNameList)){
            // 有可能会有用户自己添加的标签，所以查寻数据库中已经存在的标签。
            List<TagEntity> existedTags = tagDao.selectList(new LambdaQueryWrapper<TagEntity>()
                    .in(TagEntity::getTagName, tagNameList));
            // 转换得到已经存在的标签名
            List<String> existedTagName = existedTags.stream()
                    .map(TagEntity::getTagName)
                    .collect(Collectors.toList());
            // 转换得到已经存在的标签 ID
            List<Integer> existedTagId = existedTags.stream()
                    .map(TagEntity::getId)
                    .collect(Collectors.toList());
            // 过滤，挑选出用户自己添加的标签
            tagNameList.removeAll(existedTagName);
            // 校验过滤后的标签
            if (CollectionUtil.isNotEmpty(tagNameList)){
                // 转换并保存
                List<TagEntity> tagList = tagNameList.stream().map(item -> {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setTagName(item);
                    return tagEntity;
                }).collect(Collectors.toList());
                tagService.saveBatch(tagList);
            }
            // 标签ID 绑定文章。
            List<ArticleTagEntity> articleTagEntityList = existedTagId.stream().map(item -> {
                ArticleTagEntity articleTagEntity = new ArticleTagEntity();
                articleTagEntity.setArticleId(articleId);
                articleTagEntity.setTagId(item);
                return articleTagEntity;
            }).collect(Collectors.toList());
            articleTagService.saveBatch(articleTagEntityList);
        }
    }
}
