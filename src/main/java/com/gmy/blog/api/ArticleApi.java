package com.gmy.blog.api;

import com.gmy.blog.dto.article.ArticleDTO;
import com.gmy.blog.dto.article.ArticleHomeDTO;
import com.gmy.blog.service.ArticleService;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gmydl
 * @title: ArticleApi
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/10 12:00
 */
@RestController
@RequestMapping("/article")
public class ArticleApi {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章列表
     * @return 文章列表
     */
    @ApiOperation(value = "查询文章列表")
    @GetMapping("/list")
    public Result<List<ArticleHomeDTO>> listArticle() {
        List<ArticleHomeDTO> res = articleService.listArticle();
        return Result.ok(res);
    }

    /**
     * 查询前台文章详细信息 获取obtain
     * @return 详细信息
     */
    @ApiOperation(value = "查询文章详细信息")
    @GetMapping("/{articleId}")
    public Result<ArticleDTO> obtainArticleById(@PathVariable("articleId") Integer articleId) {
        ArticleDTO response = articleService.obtainArticleById(articleId);
        return Result.ok(response);
    }

    /**
     * 点赞文章，只能登陆后才能点赞
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "点赞文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @PostMapping("/{articleId}/like")
    public Result<?> saveArticleLike(@PathVariable("articleId") Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.ok();
    }
}
