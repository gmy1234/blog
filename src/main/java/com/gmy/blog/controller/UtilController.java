package com.gmy.blog.controller;

import com.gmy.blog.enums.FilePathEnum;
import com.gmy.blog.strategy.context.UploadStrategyContext;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gmydl
 * @title: UtilController
 * @projectName blog
 * @description: TODO
 * @date 2022/5/30 14:15
 */
@RestController
@RequestMapping("/admin/upload/")
public class UtilController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 上传文章图片
     *
     * @param file 文件
     * @return {@link Result<String>} 文章图片地址
     */
    @ApiOperation(value = "上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/articles/images")
    public Result<String> uploadArticleImages(MultipartFile file) {

        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }
}
