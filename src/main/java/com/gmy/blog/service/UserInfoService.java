package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.user.UserOnlineDTO;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.user.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;


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

    /**
     * 更新用户头像
     * @param file 头像文件
     * @return 二进制流
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 更新用户信息
     * @param userInfoVO 用户信息
     */
    void updateUserInfo(UserInfoVO userInfoVO);
}
