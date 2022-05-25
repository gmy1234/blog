package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.gmy.blog.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author gmydl
 * @title: ArticleDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/24 21:42
 */
@Repository
public interface ArticleDao extends BaseMapper<ArticleEntity> {
}
