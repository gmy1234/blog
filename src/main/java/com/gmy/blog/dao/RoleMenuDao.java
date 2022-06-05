package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.RoleMenuEntity;
import org.springframework.stereotype.Repository;

/**
 * @author gmydl
 * @title: RoleMenuDao
 * @projectName blog-api
 * @description: 角色菜单关联表
 * @date 2022/6/5 22:58
 */
@Repository
public interface RoleMenuDao extends BaseMapper<RoleMenuEntity> {

}
