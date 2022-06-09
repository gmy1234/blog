package com.gmy.blog.service;

import com.gmy.blog.dto.BlogHomeInfoDTO;

/**
 * @author gmydl
 * @title: BlogInfoService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 14:49
 */
public interface BlogInfoService {
    /**
     * 上传访问信息
     */
    void reportVisitorInfo();

    /**
     * 获取博客信息
     * @return 博客信息
     */
    BlogHomeInfoDTO getBlogHomeInfo();
}
