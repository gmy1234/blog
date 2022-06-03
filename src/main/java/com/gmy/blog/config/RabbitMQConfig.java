package com.gmy.blog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gmy.blog.constant.MQPrefixConst.*;

/**
 * @author gmydl
 * @title: RabbitMQConfig
 * @projectName blog-api
 * @description: 消息队列，创建交换机和队列,以及绑定关系
 * @date 2022/6/3 16:28
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue articleQueue() {
        return new Queue(MAXWELL_QUEUE, true);
    }

    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(MAXWELL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    /**
     *
     * @return 邮件队列
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true, false, false, null);
    }

    /**
     *
     * @return 邮件交换机
     */
    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false, null);
    }

    @Bean
    public Binding bindingEmailDirect() {
        return new Binding(EMAIL_QUEUE, Binding.DestinationType.QUEUE, EMAIL_EXCHANGE,
                "mail_routing_key", null);
        //return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

    /** json输出 **/
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
