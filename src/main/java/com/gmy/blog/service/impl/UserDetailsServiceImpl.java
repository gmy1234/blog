package com.gmy.blog.service.impl;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.dao.RoleDao;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.util.IpUtils;
import com.gmy.blog.vo.user.UserDetailDTO;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.gmy.blog.constant.RedisPrefixConst.*;
import static com.gmy.blog.enums.ZoneEnum.SHANGHAI;

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

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)) {
            throw new BizException("用户名不能为空");
        }

        // 获取用户信息
        UserAuthEntity userEntity = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuthEntity>()
                .eq(UserAuthEntity::getUsername, username));

        if (Objects.isNull(userEntity)) {
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
     * @param request    服务器请求
     * @return 用户登录信息
     */
    private UserDetailDTO convertUserDetail(UserAuthEntity userEntity, HttpServletRequest request) {
        // 查询账号信息
        UserInfoEntity userInfo = userInfoDao.selectById(userEntity.getUserInfoId());
        // 查询账号角色
        List<String> roleList = roleDao.listRolesByUserInfoId(userInfo.getId());
        // 查询账号点赞信息
        // Set<Object> articleLikeSet = redisService.sMembers(ARTICLE_USER_LIKE + userInfo.getId());
        // Set<Object> commentLikeSet = redisService.sMembers(COMMENT_USER_LIKE + userInfo.getId());
        // Set<Object> talkLikeSet = redisService.sMembers(TALK_USER_LIKE + userInfo.getId());
        // 获取设备信息
        // String ipAddress = IpUtils.getIpAddress(request);
        // String ipSource = IpUtils.getIpSource(ipAddress);
        // UserAgent userAgent = IpUtils.getUserAgent(request);
        // 封装权限集合
        return UserDetailDTO.builder()
                .id(userEntity.getId())
                .loginType(userEntity.getLoginType())
                .userInfoId(userInfo.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userInfo.getEmail())
                .roleList(roleList)
                .nickname(userInfo.getNickname())
                .avatar("null")
                .intro("intro")
                .webSite("null")
                .articleLikeSet(new HashSet<>())
                .commentLikeSet(new HashSet<>())
                .talkLikeSet(new HashSet<>())
                .ipAddress("127.0.0.1")
                .ipSource("ipSource")
                .isDisable(userInfo.getIsDisable())
                .browser("browers")
                .os("macos")
                .lastLoginTime(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())))
                .build();
    }


}
