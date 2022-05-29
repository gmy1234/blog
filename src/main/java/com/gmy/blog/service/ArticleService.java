package com.gmy.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.ArticleBackDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.vo.ArticleVo;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.DeleteVo;
import com.gmy.blog.vo.PageResult;

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
     * @param articleIds 文章 ID 集合
     */
    void deleteArticleById(DeleteVo deleteVo);
}
