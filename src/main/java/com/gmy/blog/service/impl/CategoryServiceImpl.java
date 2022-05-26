package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.CategoryDao;

import com.gmy.blog.dto.CategoryBackDTO;
import com.gmy.blog.dto.CategoryOptionDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.CategoryService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gmydl
 * @title: CategoryServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/2421:57
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageResult<CategoryBackDTO> getAllCategory(ConditionVO condition) {

        if (condition != null){
            LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(StringUtils.isNotBlank(condition.getKeywords()),
                    CategoryEntity::getCategoryName, condition.getKeywords());
            // 查询分类数量，没有的话直接返回
            Long categoryCount = categoryDao.selectCount(wrapper);
            if (categoryCount == 0) {
                return new PageResult<>();
            }

            // 分页查询 分类列表
            List<CategoryBackDTO> categoryList = categoryDao.listCategoryBackDTO(PageUtils.getCurrent() - 1 , PageUtils.getSize(), condition);
            return new PageResult<>(categoryList, Math.toIntExact(categoryCount));
        }else {
            return new PageResult<>();
        }
    }

    @Override
    public List<CategoryOptionDTO> searchArticleCategory(ConditionVO condition) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        // 关键词匹配
        wrapper.like(StringUtils.isNotBlank(condition.getKeywords()), CategoryEntity::getCategoryName, condition.getKeywords())
                // 排序
                .orderByDesc(CategoryEntity::getId);
        List<CategoryEntity> categories = categoryDao.selectList(wrapper);

        return BeanCopyUtils.copyList(categories, CategoryOptionDTO.class);
    }

    @Override
    public void saveAndUpdateCategory(CategoryVO categoryVO) {
        CategoryEntity data = CategoryEntity.builder()
                .id(categoryVO.getId())
                .categoryName(categoryVO.getCategoryName())
                .build();
        // 根据分类ID 判断是否已经存着
        CategoryEntity category = categoryDao.selectOne(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getId, categoryVO.getId()));
        if (category != null) {
            categoryDao.updateById(data);
        }else {
            this.baseMapper.insert(data);
        }

    }

    @Override
    public void deletedCategoryBatch(List<Integer> categoryIdList) {
        // 查询分类id下是否有文章
        Long articleCount = articleDao.selectCount(new LambdaQueryWrapper<ArticleEntity>()
                .in(ArticleEntity::getId, categoryIdList));
        if (articleCount != 0){
            throw new BizException("删除失败， 该分类下边有文章");
        }
        categoryDao.deleteBatchIds(categoryIdList);
    }

    @Override
    public void deletedCategory(Integer id) {
        this.baseMapper.deleteById(id);
    }


}
