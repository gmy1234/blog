package com.gmy.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gmydl
 * @title: MinioProperties
 * @projectName blog-api
 * @description: Minio的配置
 * @date 2022/7/17 18:47
 */
@Data
@ConfigurationProperties("upload.minio")
@Configuration
public class MinioProperties {

    private String bucket;

    private String endpoint;

    private String accessKey;

    private String secretKey;
}
