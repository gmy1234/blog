package com.gmy.blog.api;

import com.gmy.blog.dto.ArchiveDTO;
import com.gmy.blog.dto.CategoryDTO;
import com.gmy.blog.dto.TagDTO;
import com.gmy.blog.dto.article.ArticleDTO;
import com.gmy.blog.dto.article.ArticleHomeDTO;
import com.gmy.blog.dto.article.ArticlePreviewListDTO;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.service.ArticleService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
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
     * 查看文章归档
     *
     * @return {@link Result<ArchiveDTO>} 文章归档列表
     */
    @ApiOperation(value = "查看文章归档")
    @GetMapping("/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        PageResult<ArchiveDTO> res = articleService.listArchives();
        return Result.ok(res);
    }


    /**
     * 查询首页文章
     * @return 文章列表
     */
    @ApiOperation(value = "查询首页文章")
    @GetMapping("/homeList")
    public Result<List<ArticleHomeDTO>> listHomeArticle() {
        List<ArticleHomeDTO> res = articleService.listHomeArticle();
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

    /**
     * 文章分类列表
     *
     * @return {@link Result<>}
     */
    @ApiOperation(value = "文章分类列表")
    @GetMapping("/listCategory")
    public Result<PageResult<CategoryDTO>> listCategory() {
        PageResult<CategoryDTO> res = articleService.listCategory();
        return Result.ok(res);
    }

    /**
     * 根据条件查询文章
     *
     * @param condition 条件
     * @return {@link Result<ArticlePreviewListDTO>} 文章列表
     */
    @ApiOperation(value = "根据条件查询文章")
    @GetMapping("/listByCategoryOrTag")
    public Result<ArticlePreviewListDTO> listByCategoryOrTag(ConditionVO condition) {
        ArticlePreviewListDTO res = articleService.listByCategoryOrTag(condition);
        return Result.ok(res);
    }


    /**
     * 标签列表
     *
     * @return {@link Result<>}
     */
    @ApiOperation(value = "标签列表")
    @GetMapping("/listTag")
    public Result<PageResult<TagEntity>> listTag() {
        PageResult<TagEntity> res = articleService.listTag();
        return Result.ok(res);
    }

}
