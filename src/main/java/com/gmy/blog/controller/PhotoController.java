package com.gmy.blog.controller;

import com.gmy.blog.dto.wallpaper.PhotoBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoVO;
import com.gmy.blog.service.PhotoService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmydl
 * @title: wallpaperController
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 20:27
 */
@RestController
@RequestMapping("/admin/wallpaper")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    /**
     * 获取后台照片列表
     *
     * @param condition 条件
     * @return {@link Result<PhotoBackDTO>} 照片列表
     */
    @ApiOperation(value = "根据相册id获取照片列表")
    @GetMapping("/photos")
    public Result<PageResult<PhotoBackDTO>> listPhotos(ConditionVO condition) {
        PageResult<PhotoBackDTO> res = photoService.listPhotos(condition);
        return Result.ok(res);
    }

    /**
     * 保存照片
     *
     * @param photoVo 照片
     * @return {@link Result<PhotoBackDTO>}
     */
    @ApiOperation(value = "保存照片")
    @PostMapping("/uploadPhotos")
    public Result<?> uploadPhotos(@RequestBody PhotoVO photoVo) {
        photoService.uploadPhotos(photoVo);
        return Result.ok();
    }
}
