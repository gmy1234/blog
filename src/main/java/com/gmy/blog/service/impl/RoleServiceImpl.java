package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.RoleDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dao.UserRoleDao;
import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.entity.RoleEntity;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.entity.UserRoleEntity;
import com.gmy.blog.service.RoleService;
import com.gmy.blog.service.UserRoleService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.user.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gmydl
 * @title: RoleServiceImpl
 * @projectName blog-api
 * @description: 角色管理
 * @date 2022/6/1 23:54
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<UserRoleDTO> listUserRoles() {
        List<RoleEntity> roleList = roleDao.selectList(new LambdaQueryWrapper<RoleEntity>()
                .select(RoleEntity::getId, RoleEntity::getRoleName));

        return BeanCopyUtils.copyList(roleList, UserRoleDTO.class);
    }

    @Override
    public void updateRole(UserRoleVO userRoleVO) {
        // 更新用户信息表
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .id(userRoleVO.getUserInfoId())
                .nickname(userRoleVO.getNickname())
                .build();
        userInfoDao.updateById(userInfo);

        // 删除用户角色重新添加
        userRoleService.remove(new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getUserId, userRoleVO.getUserInfoId()));
        List<UserRoleEntity> userRoleList = userRoleVO.getRoleIdList()
                .stream()
                .map(item -> UserRoleEntity.builder()
                        .roleId(item)
                        .userId(userRoleVO.getUserInfoId())
                        .build())
                .collect(Collectors.toList());

        userRoleService.saveBatch(userRoleList);
    }
}
