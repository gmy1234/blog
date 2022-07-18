package com.gmy.blog;

import com.gmy.blog.constant.CommonConst;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author gmydl
 * @title: MinioTest
 * @projectName blog-api
 * @description: Minio 测试
 * @date 2022/7/16 17:40
 */
@SpringBootTest
public class MinioTest {

    @Value("${upload.minio.bucket:gmy}")
    private String gmyBucketName;

    @Autowired
    private MinioClient minioClient;

    @Test
    public void upload(){

        boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(gmyBucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(gmyBucketName).build());
            } else {
                System.out.println("Bucket 'gmyBucketName' already exists.");
            }
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(gmyBucketName)
                            .object("image1.png")
                            .filename("/Users/gmydl/Desktop/picture/image.png")
                            .build());
            System.out.println("文件已上传");
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e){
            System.out.println(e);
        }
    }


    /**
     * 添加文件路径
     * @throws FileNotFoundException
     */
    @Test
    public void uploadForStream() throws FileNotFoundException {
        InputStream file = new FileInputStream("/Users/gmydl/Desktop/picture/new2.png");

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(gmyBucketName)
                    .object("dir/to/p.png")
                    .stream(file, -1, 10485760)
                    .build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void downLoad() {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(gmyBucketName)
                    .object("image1.png")
                    .filename("/Users/gmydl/Desktop/picture/new2.png")
                    .build());

        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFileURL() throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        HashMap<String, String> responseType = new HashMap<>(2);
        responseType.put("response-content-type", CommonConst.CONTENT_TYPE);
        // 获取图片的URL
        String URL = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(gmyBucketName)
                .object("image1.png")
                .expiry(1, TimeUnit.DAYS)
                .build());
        System.out.println(URL);
    }


    /**
     * 获取文件的访问路径
     */
    @Test
    public void getInfo(){

        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(gmyBucketName)
                    .object("p.png").build());
            System.out.println(statObjectResponse);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
