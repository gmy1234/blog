package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.user.UserOnlineDTO;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;


/**
 * 用户信息服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
public interface UserInfoService extends IService<UserInfoEntity> {


    /**
     * 查看在线用户列表
     *
     * @param conditionVO 条件
     * @return 在线用户列表
     */
    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);
}
