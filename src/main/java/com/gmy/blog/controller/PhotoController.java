package com.gmy.blog.controller;

import com.gmy.blog.annotation.OptLog;
import com.gmy.blog.dto.wallpaper.PhotoBackDTO;
import com.gmy.blog.vo.*;
import com.gmy.blog.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;

import static com.gmy.blog.constant.OptTypeConst.SAVE;
import static com.gmy.blog.constant.OptTypeConst.UPDATE;

/**
 * @author gmydl
 * @title: wallpaperController
 * @projectName blog-api
 * @description: 后台管理系统图片模块
 * @date 2022/6/7 20:27
 */
@RestController
@RequestMapping("/admin/wallpaper")
@Api(tags = "照片模块")
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
    @OptLog(optType = SAVE)
    @ApiOperation(value = "保存照片")
    @PostMapping("/uploadPhotos")
    public Result<?> uploadPhotos(@RequestBody PhotoVO photoVo) {
        photoService.uploadPhotos(photoVo);
        return Result.ok();
    }

    /**
     * 移动照片到指定相册 其他
     *
     * @param photoVO 照片信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "移动照片到另一个相册")
    @PostMapping("/moveOtherAlbum")
    public Result<?> moveOtherAlbum(@Valid @RequestBody PhotoVO photoVO) {
        photoService.moveOtherAlbum(photoVO);
        return Result.ok();
    }

    /**
     * 更新照片删除状态(由正常——>回收箱) 回收 ——>正常
     *
     * @param deleteVO 照片信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "更新照片删除状态")
    @PostMapping("/updatePhoto")
    public Result<?> updatePhoto(@Valid @RequestBody DeleteVo deleteVO) {
        photoService.updatePhoto(deleteVO);
        return Result.ok();
    }

    /**
     * 删除照片
     *
     * @param photosIds 照片 Id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除照片")
    @PostMapping("/deletePhoto")
    public Result<?> deletePhoto( @RequestBody HashMap<String , List<Integer>> photosIds) {
        photoService.deletePhoto(photosIds.get("data"));
        return Result.ok();
    }
}
