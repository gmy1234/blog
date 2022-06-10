package com.gmy.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.ArticleTagDao;
import com.gmy.blog.dao.CategoryDao;
import com.gmy.blog.dao.TagDao;
import com.gmy.blog.dto.article.ArticleBackDTO;
import com.gmy.blog.dto.article.ArticleDTO;
import com.gmy.blog.dto.article.ArticleHomeDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.entity.ArticleTagEntity;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.*;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.util.CommonUtils;
import com.gmy.blog.vo.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.ARTICLE_SET;
import static com.gmy.blog.constant.CommonConst.FALSE;
import static com.gmy.blog.constant.RedisPrefixConst.ARTICLE_LIKE_COUNT;
import static com.gmy.blog.constant.RedisPrefixConst.ARTICLE_VIEWS_COUNT;
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

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisService redisService;

    @Override
    public void publish(ArticleVo articleVo) {
        // 获取文章的分类
        CategoryEntity category = this.saveCategory(articleVo);
        // 保存或者修改文章
        // TODO: 校验数据
        ArticleEntity articleEntity = BeanCopyUtils.copyObject(articleVo, ArticleEntity.class);
        if (Objects.nonNull(category)) {
            articleEntity.setCategoryId(category.getId());
        }
        // TODO 设置文章作者
        articleEntity.setUserId(528);
        this.saveOrUpdate(articleEntity);
        // 保存文章的新标签
        this.saveArticleTag(articleVo, articleEntity.getId());
    }

    /**
     * 保存文章分类
     *
     * @param articleVo 发布文章时提交的数据
     */
    private CategoryEntity saveCategory(ArticleVo articleVo) {
        // 获取分类名称
        String categoryName = articleVo.getCategoryName();
        CategoryEntity category = categoryDao.selectOne(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getCategoryName, categoryName));
        // 通过分类名 判断实体是否不存在，并且 文章状态不为草稿
        if (Objects.isNull(category) && !articleVo.getStatus().equals(DRAFT.getStatus())) {
            category = CategoryEntity.builder()
                    .categoryName(categoryName).build();
            categoryDao.insert(category);
        }
        return category;
    }

    /**
     * 保存创建文章时，新添加的文章标签
     *
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
        if (CollectionUtil.isNotEmpty(tagNameList)) {
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
            if (CollectionUtil.isNotEmpty(tagNameList)) {
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

    @Override
    public PageResult<ArticleBackDTO> getAllArticles(ConditionVO condition) {
        // 查文章总量
        Integer count = articleDao.getCountArticle(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        // 查询后台文章
        List<ArticleBackDTO> articleBackDTOList = articleDao
                .getListArticle(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // TODO：文章的点赞数量和收藏数量
        articleBackDTOList.forEach(item -> {
            item.setLikeCount(0);
            item.setViewsCount(0);
        });

        return new PageResult<>(articleBackDTOList, count);
    }

    @Override
    public ArticleVo getArticleById(Integer articleId) {
        // 获取文章数据
        ArticleEntity article = articleDao.selectOne(new LambdaQueryWrapper<ArticleEntity>().
                eq(ArticleEntity::getId, articleId));
        // 校验
        if (article != null) {
            ArticleVo responseArticle = BeanCopyUtils.copyObject(article, ArticleVo.class);
            // 查询文章对应的多个标签的 ID
            List<ArticleTagEntity> tagReflect = articleTagDao.selectList(new LambdaQueryWrapper<ArticleTagEntity>()
                    .eq(ArticleTagEntity::getArticleId, articleId));
            // 得到标签 ID 集合
            List<Integer> tagIds = tagReflect.stream()
                    .map(ArticleTagEntity::getTagId)
                    .collect(Collectors.toList());
            // 如果绑定了标签：校验文章是否绑定了一个或者多个 标签
            if (CollectionUtil.isNotEmpty(tagIds)) {
                // 查关联表中的数据，得到多个标签实体
                List<TagEntity> tags = tagDao.selectList(new LambdaQueryWrapper<TagEntity>()
                        .in(TagEntity::getId, tagIds));
                // 得到标签名
                List<String> tagNameList = tags.stream()
                        .map(TagEntity::getTagName)
                        .collect(Collectors.toList());
                // 设置标签名
                responseArticle.setTagNameList(tagNameList);
            }
            // 获取分类名：
            CategoryEntity category = categoryDao.selectOne(new LambdaQueryWrapper<CategoryEntity>()
                    .eq(CategoryEntity::getId, article.getCategoryId()));
            if (category != null) {
                responseArticle.setCategoryName(category.getCategoryName());
            }
            return responseArticle;
        } else {
            throw new BizException("文章 ID 不存在");
        }

    }

    @Override
    public void deleteArticleById(DeleteVo deleteVo) {
        List<ArticleEntity> articleEntityList =deleteVo.getIdList().stream()
                .map(id -> ArticleEntity.builder()
                        .id(id)
                        .isTop(FALSE)
                        .isDelete(deleteVo.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articleEntityList);
    }

    @Override
    public void updateArticleTop(ArticleTopVo articleTopVO) {
        // 修改文章置顶状态
        ArticleEntity top = ArticleEntity.builder()
                .id(articleTopVO.getId())
                .isTop(articleTopVO.getIsTop())
                .build();
        articleDao.updateById(top);
    }

    /**
     * 物理删除文章
     * @param deleteVo 文章ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void phyDeleteArticles(DeleteVo deleteVo) {
        List<Integer> articleIdList = deleteVo.getIdList();
        // 删除文章
        articleDao.deleteBatchIds(articleIdList);
        // 删除关联表
        articleTagDao.delete(new LambdaQueryWrapper<ArticleTagEntity>()
                .in(ArticleTagEntity::getArticleId, articleIdList));
    }

    @Override
    public List<ArticleHomeDTO> listArticle() {
        return articleDao.listArticle(PageUtils.getLimitCurrent(), PageUtils.getSize());
    }


    /**
     * 更新文章 浏览量
     * @param articleId 文章 ID
     */
    private void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Object attribute = session.getAttribute(ARTICLE_SET);
        Optional<Object> objectOptional = Optional.ofNullable(attribute);
        Set<Integer> articleSet = CommonUtils.castSet(objectOptional.orElseGet(HashMap::new), Integer.class);
        // 不包含
        if (!articleSet.contains(articleId)){
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // + 1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }

    }

    @Override
    public ArticleDTO obtainArticleById(Integer articleId) {
        // 获取文章详情
        ArticleDTO article = articleDao.getArticleById(articleId);
        if (Objects.isNull(article)){
            throw new BizException("文章不存在");
        }
        // TODO 查询推荐文章

        // TODO 查询最新文章

        // 更新文章浏览量
        this.updateArticleViewsCount(articleId);

        // TODO: 查询上一票下一篇文章

        // 封装点赞量和浏览量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            // 设置浏览量
            article.setViewsCount(score.intValue());
        }
        // 设置点赞量
        article.setLikeCount((Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));
        return article;
    }

}
