package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gmy.blog.dao.ArticleTagDao;
import com.gmy.blog.entity.ArticleTagEntity;
import com.gmy.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author gmydl
 * @title: ArticleTagServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/24 22:02
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTagEntity> implements ArticleTagService {

}
