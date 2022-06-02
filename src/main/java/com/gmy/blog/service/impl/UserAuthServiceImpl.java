package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户账号服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuthEntity> implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    public PageResult<UserBackDTO> getAllUsers(ConditionVO condition) {
        // 获取后台用户数量
        // condition.keywords != null
        // condition.loginType != null
        Integer count = userAuthDao.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        List<UserBackDTO> allUser =  userAuthDao.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);

        return new PageResult<>(allUser, count);
    }
}
