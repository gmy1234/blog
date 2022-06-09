package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.WebsiteConfigEntity;
import org.springframework.stereotype.Repository;

/**
 * @author gmydl
 * @title: WebsiteConfigDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/9 17:01
 */
@Repository
public interface WebsiteConfigDao extends BaseMapper<WebsiteConfigEntity> {
}
