package com.gmy.blog.controller;

import com.gmy.blog.dto.user.UserOnlineDTO;
import com.gmy.blog.service.UserInfoService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmydl
 * @title: UserInfoController
 * @projectName blog-api
 * @description: 用户信息模块
 * @date 2022/6/2 15:22
 */
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查看在线用户
     *
     * @param conditionVO 条件
     * @return {@link Result<UserOnlineDTO>} 在线用户列表
     */
    @ApiOperation(value = "查看在线用户")
    @GetMapping("/online")
    public Result<PageResult<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return Result.ok(userInfoService.listOnlineUsers(conditionVO));
    }

    /**
     * 下线用户
     *
     * @param userInfoId 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "下线用户")
    @DeleteMapping("/offline/{userInfoId}")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.ok();
    }

}
