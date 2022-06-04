package com.gmy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.constant.RedisPrefixConst;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dto.EmailDTO;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.util.CommonUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gmy.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.gmy.blog.constant.MQPrefixConst.EMAIL_ROUTING_KEY;
import static com.gmy.blog.constant.RedisPrefixConst.CODE_EXPIRE_TIME;
import static com.gmy.blog.constant.RedisPrefixConst.REGISTER_VERIFICATION_KEY;

/**
 * 用户账号服务
 *
 * @author gmy
 * @date 2022/06/01
 */
@Slf4j
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuthEntity> implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    @Override
    public PageResult<UserBackDTO> getAllUsers(ConditionVO condition) {
        // 获取后台用户数量
        // condition.keywords != null
        // condition.loginType != null
        Integer count = userAuthDao.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        List<UserBackDTO> allUser = userAuthDao.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);

        return new PageResult<>(allUser, count);
    }

    @Override
    public void sendCode(String email) {
        // 检验是否 为邮箱
        if (!CommonUtils.checkEmail(email)) {
            throw new BizException("邮箱格式错误，请输入正确的邮箱");
        }
        // 生产验证码
        String randomCode = CommonUtils.getRandomCode();
        // 封装实体类
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject("验证码")
                .content("你的验证码为： " + randomCode + ", 有效期为15分钟，请不要透露给别人")
                .build();

        // 使用消息队列，将发送验证码任务放到队列中
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_ROUTING_KEY, emailDTO);
        // 使用 Redis，存放验证码，过期时间为 15分钟
        redisService.set(REGISTER_VERIFICATION_KEY + email, randomCode, CODE_EXPIRE_TIME);

    }



}
