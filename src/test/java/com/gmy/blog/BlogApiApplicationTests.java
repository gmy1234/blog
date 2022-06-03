package com.gmy.blog;

import com.alibaba.fastjson.JSON;
import com.gmy.blog.constant.MQPrefixConst;
import com.gmy.blog.dto.EmailDTO;
import com.gmy.blog.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@SpringBootTest
class BlogApiApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮件主题");
        message.setText("邮件内容:验证码");
        message.setTo("1544126485@qq.com");
        message.setFrom("1508594767@qq.com");
        javaMailSender.send(message);
        System.out.println("发送成功");

    }


}
