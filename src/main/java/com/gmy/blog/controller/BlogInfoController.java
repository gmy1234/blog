package com.gmy.blog.controller;

import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.WebsiteConfigVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmydl
 * @title: BlogInfoController
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/9 11:47
 */
@RestController
@RequestMapping("/admin/blog")
public class BlogInfoController {

    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 获取网站配置
     *
     * @return {@link Result<WebsiteConfigVO>} 网站配置
     */
    @ApiOperation(value = "获取网站配置")
    @GetMapping("/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        WebsiteConfigVO response = blogInfoService.getWebsiteConfig();
        return Result.ok(response);
    }
}
