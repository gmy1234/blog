package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.vo.user.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author gmydl
 * @title: UserDetailsServiceImpl
 * @projectName blog-api
 * @description: 使用 Spring Security 来进行登陆
 * @date 2022/6/4 19:51
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)){
            throw new BizException("用户名不能为空");
        }

        // 获取用户信息
        UserAuthEntity userEntity = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuthEntity>()
                .eq(UserAuthEntity::getUsername, username));

        if (Objects.isNull(userEntity)){
            throw new BizException("用户名不存在！");

        }
        return this.convertUserDetail(userEntity, request);

        // 返回 UserDetails



        // TODO：查询对应的权限信息

    }

    /**
     * 封装用户的信息
     *
     * @param userEntity 用户注册时，账号信息
     * @param request 服务器请求
     * @return 用户登录信息
     */
    private UserDetailDTO convertUserDetail(UserAuthEntity userEntity, HttpServletRequest request) {




        return UserDetailDTO.builder().build();
    }
}
