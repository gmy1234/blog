package com.gmy.blog.api;

import com.gmy.blog.annotation.AccessLimit;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmydl
 * @title: UserAuthController
 * @projectName blog
 * @description: 用户账号模块
 * @date 2022/6/1 20:44
 */
@Api("用户账号模块")
@RestController
@RequestMapping("/user")
public class UserAuthApi {

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    @GetMapping("/code")
    public Result<?> sendCode(String username) {
        userAuthService.sendCode(username);
        return Result.ok();
    }

    /**
     * 用户注册
     *
     * @param userVo 用户注册的信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result<?> register(@RequestBody UserVO userVo) {
        userAuthService.register(userVo);
        return Result.ok();
    }

    /**
     * 用户注册
     *
     * @param userVo 用户登陆信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "用户登陆")
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserVO userVo) {
        String token = userAuthService.login(userVo);
        return Result.ok(token);
    }

    @GetMapping("/hello")
    public Result<String> hello() {

        return Result.ok("hello");
    }

    @GetMapping("/logout")
    public Result<String > logout() {

        return Result.ok(userAuthService.logout());
    }
}
