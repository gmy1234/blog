package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.ArticleTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author gmydl
 * @title: ArticleTagDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/24 22:04
 */
@Repository
public interface ArticleTagDao extends BaseMapper<ArticleTagEntity> {
}
