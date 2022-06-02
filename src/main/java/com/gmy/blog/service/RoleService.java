package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.entity.RoleEntity;

import java.util.List;

/**
 * @author gmydl
 * @title: RoleService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/1 23:53
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 获取用户角色选项
     *
     * @return 角色
     */
    List<UserRoleDTO> listUserRoles();
}
