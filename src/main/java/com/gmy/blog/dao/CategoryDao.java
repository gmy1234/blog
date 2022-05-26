package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.CategoryBackDTO;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmydl
 * @title: CategoryDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/2421:54
 */
@Repository
public interface CategoryDao extends BaseMapper<CategoryEntity> {
    /**
     * 查询后台分类列表
     * @param current 当前页码
     * @param size 页码大小
     * @param condition 条件
     * @return 返回需要的数据
     */
    List<CategoryBackDTO> listCategoryBackDTO(@Param("currentPage") Long current, @Param("pageSize") Long size, @Param("condition") ConditionVO condition);
}
