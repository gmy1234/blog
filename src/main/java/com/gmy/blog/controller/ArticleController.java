package com.gmy.blog.controller;

import com.gmy.blog.service.ArticleService;
import com.gmy.blog.vo.ArticleVo;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmydl
 * @title: ArticleController
 * @projectName blog
 * @description: 文章模块
 * @date 2022/5/24 21:49
 */
@Api("文章模块")
@CrossOrigin
@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @ApiOperation(value = "保存草稿")
    @PostMapping("/saveDraft")
    public Result<?> saveDraft(){
        return new Result<>();
    }

    @ApiOperation(value = "发布文章")
    @PostMapping("/publish")
    public Result<?> publish(@RequestBody ArticleVo articleVo){
        articleService.publish(articleVo);
        return  Result.ok();
    }
}
