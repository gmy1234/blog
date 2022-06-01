package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.RoleDao;
import com.gmy.blog.entity.RoleEntity;
import com.gmy.blog.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author gmydl
 * @title: RoleServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/1 23:54
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {
}
