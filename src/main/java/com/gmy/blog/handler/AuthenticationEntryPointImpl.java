package com.gmy.blog.handler;

import com.alibaba.fastjson.JSON;
import com.gmy.blog.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gmy.blog.constant.CommonConst.APPLICATION_JSON;

/**
 * @author gmydl
 * @title: AuthenticationEntryPointImpl
 * @projectName blog-api
 * @description: (用户未登录处理) 认证处理异常，当用户登陆过程中，出现的错误，
 * 用户名不存在或者密码错误，就抛出了这个异常
 * @date 2022/6/5 23:25
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 处理异常
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.fail("认证失败")));
    }
}
