package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.TagEntity;
import org.springframework.stereotype.Repository;

/**
 * @author gmydl
 * @title: TagDao
 * @projectName blog
 * @description: TODO
 * @date 2022/5/27 12:17
 */
@Repository
public interface TagDao extends BaseMapper<TagEntity> {
}
