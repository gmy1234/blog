package com.gmy.blog.api;

import com.gmy.blog.dto.article.ArticleDTO;
import com.gmy.blog.dto.article.ArticleHomeDTO;
import com.gmy.blog.service.ArticleService;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return
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
}
