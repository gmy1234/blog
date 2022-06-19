package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.CommentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author gmydl
 * @title: CommentEntity
 * @projectName blog-api
 * @description: 评论实体
 * @date 2022/6/19 23:51
 */
@Repository
public interface CommentDao extends BaseMapper<CommentEntity> {



}
