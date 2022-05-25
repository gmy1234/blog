package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * @author gmydl
 * @title: ArticleService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/2421:46
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {
}
