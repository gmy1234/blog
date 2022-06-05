package com.gmy.blog.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.vo.user.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.BindException;
import java.util.Objects;

/**
 * @author gmydl
 * @title: JwtAuthenticationTokenFilter
 * @projectName blog-api
 * @description: 认证过滤器
 * @date 2022/6/5 13:51
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取Token
        String token = request.getHeader("token");
        // 解析 Token
        if (StringUtils.isBlank(token)){
            // 放行
            filterChain.doFilter(request, response);
            return ;
        }

        // 解析 Token
        JWT jwt = new JWT();
        JWT parse = jwt.parse(token);
        JWTPayload payload = parse.getPayload();
        String  userId = (String) payload.getClaim("userId");

        // redis 中获取用户信息
        Object userInfo = redisService.get("login:" + userId);
        if (Objects.isNull(userInfo)){
            throw new BindException("UserID 非法 或 用户未登陆");
        }

        // 存入 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userInfo, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行：
        filterChain.doFilter(request,response);
    }
}
