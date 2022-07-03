package com.gmy.blog.consumer;

import com.alibaba.fastjson.JSON;
import com.gmy.blog.constant.MQPrefixConst;
import com.gmy.blog.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gmydl
 * @title: EmailConsumer
 * @projectName blog-api
 * @description: 发送邮件的消费者
 * @date 2022/6/4 11:07
 */
@Component
@Slf4j
@PropertySource("classpath:/application-dev.yaml" ) // 指定配置文件，用@Value来获取配置文件中的值
public class EmailConsumer {

    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送消息的消费者
     * @param emailDTO 发送消息
     */
    @RabbitHandler
    @RabbitListener(queues = MQPrefixConst.EMAIL_QUEUE)
    public void process(EmailDTO emailDTO) {
        log.info("从队列接受到消息了，准备发送邮件了");
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        // 设置发送邮箱主题
        mailMessage.setSubject(emailDTO.getSubject());
        // 设置发送邮箱文本
        mailMessage.setText(emailDTO.getContent());
        // 设置发送目的地邮箱
        mailMessage.setTo(emailDTO.getEmail());
        mailMessage.setFrom(email);
        javaMailSender.send(mailMessage);
        log.info("发送邮件成功");
    }




}
