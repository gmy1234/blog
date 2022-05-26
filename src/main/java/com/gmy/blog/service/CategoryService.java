package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.CategoryBackDTO;
import com.gmy.blog.dto.CategoryOptionDTO;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.vo.CategoryVO;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;

import java.util.List;

/**
 * @author gmydl
 * @title: CategoryService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/2421:57
 */
public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 查询 分类信息和 分类对应的文章数量
     * @param condition 查询条件
     * @return 结果
     */
    PageResult<CategoryBackDTO> getAllCategory(ConditionVO condition);

    /**
     * 搜索文章分类
     *
     * @param condition 搜索条件
     * @return 对应的文章分类
     */
    List<CategoryOptionDTO> searchArticleCategory(ConditionVO condition);

    /**
     * 添加或者修改 分类
     * @param categoryVO 分类信息
     */
    void saveAndUpdateCategory(CategoryVO categoryVO);

    /**
     * 删除 分类
     * @param categoryIdList 分类的ID集合
     */
    void deletedCategoryBatch(List<Integer> categoryIdList);

    void deletedCategory(Integer id);
}
