package com.gmy.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gmydl
 * @title: OssConfigProperties
 * @projectName blog
 * @description: OSS 上传属性配置
 * @date 2022/5/30 14:29
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "upload.oss")
public class OssConfigProperties {

    /**
     * oss域名
     */
    private String url;

    /**
     * 终点
     */
    private String endpoint;

    /**
     * 访问密钥id
     */
    private String accessKeyId;

    /**
     * 访问密钥密码
     */
    private String accessKeySecret;

    /**
     * bucket名称
     */
    private String bucketName;
}
