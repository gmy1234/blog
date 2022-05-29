package com.gmy.blog.controller;

import com.gmy.blog.dto.ArticleBackDTO;
import com.gmy.blog.service.ArticleService;
import com.gmy.blog.vo.ArticleVo;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation(value = "后台查询所有文章")
    @GetMapping("/getAll")
    public Result<PageResult<ArticleBackDTO>> getAllArticle(ConditionVO conditionVO){
        PageResult<ArticleBackDTO> data = articleService.getAllArticles(conditionVO);
        return  Result.ok(data);
    }

    @ApiOperation(value = "通过 ID 获取后台文章详情")
    @GetMapping("/getArticle/{articleId}")
    public Result<ArticleVo> getArticleById(@PathVariable("articleId") Integer articleId){
        ArticleVo res = articleService.getArticleById(articleId);
        return  Result.ok(res);
    }
}
