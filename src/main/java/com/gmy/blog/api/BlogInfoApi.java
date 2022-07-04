package com.gmy.blog.api;

import com.gmy.blog.dto.BlogHomeInfoDTO;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmydl
 * @title: BlogInfoApi
 * @projectName blog-api
 * @description: 博客基本信息实体
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

    /**
     * 查看博客信息
     *
     * @return {@link Result<BlogHomeInfoDTO>} 博客信息
     */
    @ApiOperation(value = "查看博客信息")
    @GetMapping("/info")
    public Result<BlogHomeInfoDTO> getBlogHomeInfo() {

        BlogHomeInfoDTO res = blogInfoService.getBlogHomeInfo();
        return Result.ok(res);
    }
}
