package com.gmy.blog.api;

import com.gmy.blog.annotation.AccessLimit;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dto.user.UserInfoDTO;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.service.UserInfoService;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.user.UserInfoVO;
import com.gmy.blog.vo.user.UserLoginVo;
import com.gmy.blog.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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
public class UserApi {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserInfoService userInfoService;

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
     * @param userLoginVo 用户登陆信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "用户登陆")
    @PostMapping("/login")
    public Result<UserInfoDTO> login(@RequestBody UserLoginVo userLoginVo) {
        UserInfoDTO userInfo = userAuthService.login(userLoginVo);

        return Result.ok(userInfo);
    }

    @GetMapping("/hello")
    @PreAuthorize(value = "hasAuthority('user')")
    public Result<String> hello() {

        return Result.ok("hello");
    }

    /**
     * 用户重置密码
     *
     * @param userVO 用户登陆信息
     * @return {@link Result<>}
     */
    @PostMapping("/resetPassword")
    public Result<?> resetPassword(@RequestBody UserVO userVO) {
        userAuthService.resetPassword(userVO);
        return Result.ok();
    }

    @GetMapping("/logout")
    @PreAuthorize(value = "hasAuthority('user')")
    public Result<String > logout() {

        return Result.ok(userAuthService.logout());
    }


    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "更新用户信息")
    @PreAuthorize(value = "hasAuthority('user')")
    @PutMapping("/info/update")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.ok();
    }

    /**
     * 更新用户头像
     *
     * @param file 文件
     * @return {@link Result<String>} 头像地址
     */
    @ApiOperation(value = "更新用户头像")
    @PreAuthorize(value = "hasAuthority('user')")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/info/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        String res = userInfoService.updateUserAvatar(file);
        return Result.ok(res);
    }
}
