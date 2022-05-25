package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.CategoryOptionDTO;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.vo.ConditionVO;

import java.util.List;

/**
 * @author gmydl
 * @title: CategoryService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/2421:57
 */
public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryEntity> getAllCategory();

    /**
     * 搜索文章分类
     *
     * @param condition 搜索条件
     * @return 对应的文章分类
     */
    List<CategoryOptionDTO> searchArticleCategory(ConditionVO condition);
}
