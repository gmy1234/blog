package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.CommentDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.entity.CommentEntity;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.service.RedisService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gmydl
 * @title: CommentServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/19 23:54
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BlogInfoService blogInfoService;
}
