package com.gmy.blog.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author gmydl
 * @title: UploadStrategy
 * @projectName blog
 * @description: 上传策略抽象类
 * @date 2022/5/30 14:17
 */
public interface UploadStrategy {
    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 上传路径
     * @return {@link String} 文件地址
     */
    String uploadFile(MultipartFile file, String path);
}
