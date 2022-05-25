package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.CategoryDao;

import com.gmy.blog.dto.CategoryBackDTO;
import com.gmy.blog.dto.CategoryOptionDTO;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.service.CategoryService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
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

    @Override
    public List<CategoryEntity> getAllCategory() {
        List<CategoryEntity> entities = this.baseMapper.selectList(new QueryWrapper<>());
        System.out.println(entities);
        return entities;
    }

    @Override
    public List<CategoryOptionDTO> searchArticleCategory(ConditionVO condition) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        // 关键词匹配
        wrapper.like(StringUtils.isNotBlank(condition.getKeywords()), CategoryEntity::getCategoryName, condition.getKeywords())
                // 排序
                .orderByDesc(CategoryEntity::getId);

        List<CategoryEntity> categories = categoryDao.selectList(wrapper);
        List<CategoryOptionDTO> res = BeanCopyUtils.copyList(categories, CategoryOptionDTO.class);

        return res;
    }


}
