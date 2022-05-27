package com.gmy.blog.handler;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmy.blog.vo.PageUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.gmy.blog.constant.CommonConst.*;

/**
 * @author gmydl
 * @title: PageableHandlerInterceptor
 * @projectName blog
 * @description: 分页拦截器
 * @date 2022/5/27 16:45
 */
public class PageableHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentPage = request.getParameter(CURRENT);
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        if (!StringUtils.isNullOrEmpty(currentPage)) {
            PageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        PageUtils.remove();
    }
}
