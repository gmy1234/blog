package com.gmy.blog.controller;

import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmydl
 * @title: UserAuthController
 * @projectName blog-api
 * @description: 用户账号模块
 * @date 2022/6/2 13:05
 */
@CrossOrigin
@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/admin/user")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 查询后台用户列表
     *
     * @param condition 条件
     * @return {@link Result<UserBackDTO>} 用户列表
     */
    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/getAllUser")
    public Result<PageResult<UserBackDTO>> getAllUsers(ConditionVO condition) {
        return Result.ok(userAuthService.getAllUsers(condition));
    }
}
