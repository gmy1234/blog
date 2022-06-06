package com.gmy.blog.handler;

import com.alibaba.fastjson.JSON;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dto.user.UserInfoDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.util.UserUtils;
import com.gmy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gmy.blog.constant.CommonConst.APPLICATION_JSON;

/**
 * @author gmydl
 * @title: AuthenticationSuccessHandlerImpl
 * @projectName blog-api
 * @description: 用户登陆成功的处理
 * @date 2022/6/6 00:45
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("认证成功了");
        // 返回登录信息
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), UserInfoDTO.class);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.ok(userLoginDTO)));
        // 更新用户ip，最近登录时间
        this.updateUserInfo();
    }
    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        UserAuthEntity userAuth = UserAuthEntity.builder()
                .id(UserUtils.getLoginUser().getId())
                .ipAddress(UserUtils.getLoginUser().getIpAddress())
                .ipSource(UserUtils.getLoginUser().getIpSource())
                .lastLoginTime(UserUtils.getLoginUser().getLastLoginTime())
                .build();
        userAuthDao.updateById(userAuth);
    }

}
