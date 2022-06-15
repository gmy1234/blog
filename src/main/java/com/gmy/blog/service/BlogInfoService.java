package com.gmy.blog.service;

import com.gmy.blog.dto.BlogBackInfoDTO;
import com.gmy.blog.dto.BlogHomeInfoDTO;
import com.gmy.blog.vo.WebsiteConfigVO;

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

    /**
     * 获取网站配置
     * @return 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();

    /**
     * 更新网站配置
     * @param websiteConfigVO 网站信息
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    /**
     * 获取博客后台信息
     * @return 后台信息
     */
    BlogBackInfoDTO getBlogBasicInfo();
}
