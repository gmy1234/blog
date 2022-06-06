package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.user.UserLoginVo;
import com.gmy.blog.vo.user.UserVO;

import java.util.List;


/**
 * 用户账号服务
 *
 * @author Gmy
 * @date 2022.6.3-00.20
 */
public interface UserAuthService extends IService<UserAuthEntity> {


    /**
     * 获取 用户列表
     * @param condition 查询条件
     * @return 用户
     */
    PageResult<UserBackDTO> getAllUsers(ConditionVO condition);

    /**
     * 用户发送验证码
     * @param email 用户的邮箱
     */
    void sendCode(String email);

    /**
     * 用户注册
     * @param userVo 用户的信息
     */
    void register(UserVO userVo);

    /**
     * 用户登陆
     * @param userVo 登陆信息
     */
    String  login(UserLoginVo userVo);

    /**
     * 退出登陆
     * @return 消息
     */
    String  logout();

}
