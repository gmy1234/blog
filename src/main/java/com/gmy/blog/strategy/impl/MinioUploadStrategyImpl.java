package com.gmy.blog.strategy.impl;

import com.gmy.blog.config.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author guanmy
 * @title: MinioUploadStrategyImpl
 * @projectName blog
 * @description: Minio 上传
 * @date 2022/7/18 16:39
 */
@Service("minioUploadStrategyImpl")
public class MinioUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    private MinioClient minioClient;

    @Value("${upload.minio.bucket}")
    private String bucket;

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public Boolean exists(String filePath) {
        try {
            GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath).build());
            return object != null;
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws IOException {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(path + fileName)
                    .stream(inputStream, -1, 10485760)
                    .build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return minioProperties.getEndpoint() + "/" + bucket + filePath;
    }
}
