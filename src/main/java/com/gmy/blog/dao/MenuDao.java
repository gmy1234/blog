package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.MenuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmydl
 * @title: MenuDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/5 22:52
 */
@Repository
public interface MenuDao extends BaseMapper<MenuEntity> {

    /**
     * 根据用户id查询菜单
     * @param userInfoId 用户信息id
     * @return 菜单列表
     */
    List<MenuEntity> listMenusByUserInfoId(@Param("userInfoId") Integer userInfoId);

}
