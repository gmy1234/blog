package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author gmydl
 * @title: CategoryDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/2421:54
 */
@Repository
public interface CategoryDao extends BaseMapper<CategoryEntity> {
}
