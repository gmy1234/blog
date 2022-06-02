package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dto.user.UserOnlineDTO;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.service.UserInfoService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {


    @Override
    public PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO) {
        // TODO:spring aq
        return new PageResult<>();
    }
}
