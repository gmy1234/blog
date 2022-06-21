package com.gmy.blog.util;

import com.gmy.blog.vo.user.UserDetailDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * 用户工具类
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Component
public class UserUtils {

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static UserDetailDTO getLoginUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (UserDetailDTO) authentication.getPrincipal();
    }

}
