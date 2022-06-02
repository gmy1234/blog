package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dto.EmailDTO;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.util.CommonUtils;
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

    @Override
    public void sendCode(String username) {
        // 检验是否 为邮箱
        if (!CommonUtils.checkEmail(username)) {
            throw new BizException("邮箱格式错误，请输入正确的邮箱");
        }
        // 生产验证码
        String randomCode = CommonUtils.getRandomCode();
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .content("你的验证码为： " + randomCode + ", 有效期为15分钟，请不要透露给别人")
                .build();

        // 使用消息队列，将发送验证码任务放到队列中

        // 使用 Redis，存放验证码，过期时间为 15分钟

    }
}
