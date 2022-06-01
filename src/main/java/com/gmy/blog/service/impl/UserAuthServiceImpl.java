package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * 用户账号服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuthEntity> implements UserAuthService {

}
