package com.gmy.blog.strategy.context;

import com.gmy.blog.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.gmy.blog.enums.UploadModeEnum.getStrategy;

/**
 * @author gmydl
 * @title: UploadStrategyContext
 * @projectName blog
 * @description: 上传策略上下文
 * @date 2022/5/30 14:18
 */
@Service
public class UploadStrategyContext {
    /**
     * 上传模式
     */
    @Value("${upload.mode}")
    private String uploadMode;

    /**
     * key ：上传方式 OSS和 本地
     * value: 上传策略
     */
    @Autowired
    private Map<String, UploadStrategy> uploadStrategyMap;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        // 通过上传方式获取上传策略
        UploadStrategy uploadStrategy = uploadStrategyMap.get(getStrategy(uploadMode));
        // 上传文件，返回文件地址
        String filePath = uploadStrategy.uploadFile(file, path);
        return filePath;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path, String uploadMode) {
        // 通过上传方式获取上传策略
        UploadStrategy uploadStrategy = uploadStrategyMap.get(uploadMode);
        // 上传文件，返回文件地址
        String filePath = uploadStrategy.uploadFile(file, path);
        return filePath;
    }
}
