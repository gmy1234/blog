package com.gmy.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.vo.ArticleVo;

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
}
