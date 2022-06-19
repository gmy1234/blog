package com.gmy.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.CategoryDTO;
import com.gmy.blog.dto.article.ArticleBackDTO;
import com.gmy.blog.dto.article.ArticleDTO;
import com.gmy.blog.dto.article.ArticleHomeDTO;
import com.gmy.blog.dto.article.ArticlePreviewListDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.vo.*;

import java.util.List;

/**
 * 文章服务
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
public interface ArticleService extends IService<ArticleEntity> {


    /**
     * 发布文章
     * @param articleVo 文章的信息
     */
    void publish(ArticleVo articleVo);

    /**
     * 后台  查询 分页 所有文章
     * @param conditionVO 分页条件
     * @return 文章列表
     */
    PageResult<ArticleBackDTO> getAllArticles(ConditionVO conditionVO);

    /**
     * 通过 ID 获取后台文章详情
     * @param articleId 文章 ID
     * @return 文章
     */
    ArticleVo getArticleById(Integer articleId);

    /**
     * 逻辑删除 文章
     * @param deleteVo 文章 ID 集合 和 删除标识
     */
    void deleteArticleById(DeleteVo deleteVo);

    /**
     * 更新置顶状态
     * @param articleTopVO 请求体
     */
    void updateArticleTop(ArticleTopVo articleTopVO);

    /**
     * 物理删除文章
     * @param articleIdList 文章ID集合
     */
    void phyDeleteArticles(DeleteVo articleIdList);

    /**
     * 前台 获取 首页文章
     * @return 首页文章
     */
    List<ArticleHomeDTO> listHomeArticle();

    /**
     * 通过文章ID获取前台文章信息
     * @param articleId 文章ID
     * @return 文章信息
     */
    ArticleDTO obtainArticleById(Integer articleId);

    /**
     * 点赞文章
     * @param articleId 文章ID
     */
    void saveArticleLike(Integer articleId);

    /**
     * 分类列表
     * @return 分类
     */
    PageResult<CategoryDTO> listCategory();

    /**
     * 不同分类下的文章列表
     * @param condition 条件
     * @return
     */
    ArticlePreviewListDTO listByCategory(ConditionVO condition);
}
