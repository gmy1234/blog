package com.gmy.blog.controller;

import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.service.RoleService;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.user.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gmydl
 * @title: RoleController
 * @projectName blog-api
 * @description: 角色模块
 * @date 2022/6/2 14:15
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询用户角色选项
     *
     * @return {@link Result<UserRoleDTO>} 用户角色选项
     */
    @ApiOperation(value = "查询用户角色选项")
    @GetMapping("/getRole")
    public Result<List<UserRoleDTO>> listUserRoles() {
        return Result.ok(roleService.listUserRoles());
    }

    /**
     * 修改用户角色选项
     *
     * @return {@link Result<UserRoleDTO>} 用户角色选项
     */
    @ApiOperation(value = "修改用户角色选项")
    @PostMapping("/updateRole")
    public Result<?> updateRole(@RequestBody UserRoleVO userInfo) {
        System.out.println(userInfo);
        roleService.updateRole(userInfo);
        return Result.ok();
    }
}
