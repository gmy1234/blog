package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {


}
