package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author gmydl
 * @title: RoleDao
 * @projectName blog-api
 * @description: 角色Dao
 * @date 2022/6/1 23:51
 */
@Repository
public interface RoleDao extends BaseMapper<RoleEntity> {

    /**
     * 根据用户id获取角色列表
     *
     * @param userInfoId 用户id
     * @return 角色标签
     */
    List<String> listRolesByUserInfoId(Integer userInfoId);
}
