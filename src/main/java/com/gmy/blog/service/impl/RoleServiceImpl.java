package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.RoleDao;
import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.entity.RoleEntity;
import com.gmy.blog.service.RoleService;
import com.gmy.blog.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gmydl
 * @title: RoleServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/1 23:54
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<UserRoleDTO> listUserRoles() {
        List<RoleEntity> roleList = roleDao.selectList(new LambdaQueryWrapper<RoleEntity>()
                .select(RoleEntity::getId, RoleEntity::getRoleName));

          return BeanCopyUtils.copyList(roleList, UserRoleDTO.class);
    }
}
