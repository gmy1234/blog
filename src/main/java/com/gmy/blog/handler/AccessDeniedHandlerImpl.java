package com.gmy.blog.handler;

import com.alibaba.fastjson.JSON;
import com.gmy.blog.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gmy.blog.constant.CommonConst.APPLICATION_JSON;

/**
 * @author gmydl
 * @title: AccessDeniedHandlerImpl
 * @projectName blog-api
 * @description: (用户权限处理) 当用户没有权限，操作抛出当异常
 * @date 2022/6/5 23:32
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.fail("权限不足")));
    }
}
