package com.gmy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.constant.MQPrefixConst;
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
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.util.List;

/**
 * 用户账号服务
 *
 * @author gmy
 * @date 2022/06/01
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuthEntity> implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

        Message emailMessage = new Message(JSON.toJSONBytes(emailDTO));
        // 使用消息队列，将发送验证码任务放到队列中
//        rabbitTemplate.convertAndSend(MQPrefixConst.EMAIL_EXCHANGE, "*", emailMessage,
//                new CorrelationData());

        rabbitTemplate.convertAndSend(MQPrefixConst.EMAIL_EXCHANGE, "mail_routing_key",
                emailMessage);




    }


    // 使用 Redis，存放验证码，过期时间为 15分钟

}
