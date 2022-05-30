package com.gmy.blog.strategy.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gmy.blog.config.OssConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author gmydl
 * @title: OssUploadStrategyImpl
 * @projectName blog
 * @description: TODO
 * @date 2022/5/30 14:28
 */
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends AbstractUploadStrategyImpl{

    @Autowired
    private OssConfigProperties ossConfigProperties;

    @Override
    public Boolean exists(String filePath) {
        return getOssClient()
                .doesObjectExist(ossConfigProperties.getBucketName(), filePath);
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        getOssClient().putObject(
                ossConfigProperties.getBucketName(),
                path + fileName,
                inputStream);

    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return ossConfigProperties.getUrl() + filePath;
    }

    /**
     * 获取ossClient
     *
     * @return {@link OSS} ossClient
     */
    private OSS getOssClient() {
        OSSClientBuilder ossClientBuilder = new OSSClientBuilder();
        return ossClientBuilder.build(
                ossConfigProperties.getEndpoint(),
                ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());

    }
}
