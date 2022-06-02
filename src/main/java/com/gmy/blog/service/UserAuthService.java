package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;

import java.util.List;


/**
 * 用户账号服务
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
public interface UserAuthService extends IService<UserAuthEntity> {


    /**
     * 获取 用户列表
     * @param condition 查询条件
     * @return 用户
     */
    PageResult<UserBackDTO> getAllUsers(ConditionVO condition);
}
