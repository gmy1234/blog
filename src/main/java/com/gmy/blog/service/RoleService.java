package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.entity.RoleEntity;
import com.gmy.blog.vo.user.UserRoleVO;

import java.util.List;

/**
 * @author gmydl
 * @title: RoleService
 * @projectName blog-api
 * @description: 角色管理
 * @date 2022/6/1 23:53
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 获取用户角色选项
     *
     * @return 角色
     */
    List<UserRoleDTO> listUserRoles();

    /**
     * 更改用户角色
     * @param userInfo 用户信息
     */
    void updateRole(UserRoleVO userInfo);
}
