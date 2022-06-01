package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.UserAuthEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户账号
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Repository
public interface UserAuthDao extends BaseMapper<UserAuthEntity> {

}
