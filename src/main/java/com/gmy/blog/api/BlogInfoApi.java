package com.gmy.blog.api;

import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmydl
 * @title: BlogInfoApi
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 14:42
 */
@RestController
@RequestMapping("/blog")
public class BlogInfoApi {

    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 上传访客信息,当用户点击文章的时候，上传访客信息 visitor
     *
     * @return {@link Result}
     */
    @PostMapping("/reportVisitor")
    public Result<?> reportVisitorInfo() {
        blogInfoService.reportVisitorInfo();
        return Result.ok();
    }
}
