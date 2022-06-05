package com.gmy.blog;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson.JSON;
import com.gmy.blog.constant.MQPrefixConst;
import com.gmy.blog.dto.EmailDTO;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class BlogApiApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void sendEmail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("邮件主题");
//        message.setText("邮件内容:验证码");
//        message.setTo("1544126485@qq.com");
//        message.setFrom("1508594767@qq.com");
//        javaMailSender.send(message);
//        System.out.println("发送成功");

    }

    @Test
    void redisTest(){
        //redisService.set("user_code: gmy", 32421,  10 * 60);

    }

    @Test
    void jwtTest(){

        String key = "aabb";
        String key2 = "token";
        Map<String,Object> payload = new HashMap<>();
        payload.put("userName", "zhangsan");
        payload.put("passWord", "666889");
        String token = JWTUtil.createToken(payload, key.getBytes(StandardCharsets.UTF_8));
        System.out.println(token);

        Map<String,Object> payload2 = new HashMap<>();
        payload2.put("userId", "1004");
        String token2 = JWTUtil.createToken(payload2, key.getBytes(StandardCharsets.UTF_8));
        System.out.println(token2);

        String token3 = JWTUtil.createToken(payload2, key2.getBytes(StandardCharsets.UTF_8));
        System.out.println(token3);

        // 总结 key 是定义 尾部的验证信息， payload 是定义中间的有效信息，head定义信息头。
    }
}
