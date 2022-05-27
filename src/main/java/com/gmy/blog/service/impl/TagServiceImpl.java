package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.TagDao;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.service.TagService;
import org.springframework.stereotype.Service;

/**
 * @author gmydl
 * @title: TagServiceImpl
 * @projectName blog
 * @description: TODO
 * @date 2022/5/27 12:19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {
}
