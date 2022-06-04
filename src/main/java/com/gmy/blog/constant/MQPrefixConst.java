package com.gmy.blog.constant;

/**
 * mqprefix 常量
 * mq常量
 */
public class MQPrefixConst {

    /**
     * maxwell交换机
     */
    public static final String MAXWELL_EXCHANGE = "maxwell_exchange";

    /**
     * maxwell队列
     */
    public static final String MAXWELL_QUEUE = "maxwell_queue";

    /**
     * email交换机
     */
    public static final String EMAIL_EXCHANGE = "email_exchange";

    /**
     * 邮件队列
     */
    public static final String EMAIL_QUEUE = "email_queue";


    /**
     * 邮件交换机和队列的绑定关系
     */
    public static final String EMAIL_ROUTING_KEY = "mail_routing_key";
}
