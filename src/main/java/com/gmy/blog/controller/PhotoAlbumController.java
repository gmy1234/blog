package com.gmy.blog.controller;

import com.gmy.blog.annotation.OptLog;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumVO;
import com.gmy.blog.enums.FilePathEnum;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.strategy.context.UploadStrategyContext;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.gmy.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * @author gmydl
 * @title: PhotoAlbumController
 * @projectName blog-api
 * @description: 相册模块
 * @date 2022/6/7 20:57
 */
@RestController
@RequestMapping("/admin/album")
public class PhotoAlbumController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private PhotoAlbumService photoAlbumService;

    /**
     * 上传相册封面
     *
     * @param file 文件
     * @return {@link Result<String>} 相册封面地址
     */
    @ApiOperation(value = "上传相册封面")
    @ApiImplicitParam(name = "file", value = "相册封面", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadCover")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }
    /**
     * 获取相册列表
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表
     */
    @ApiOperation(value = "获取所有相册列表")
    @GetMapping("/list")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        List<PhotoAlbumDTO> response = photoAlbumService.listPhotoAlbums();
        return Result.ok(response);
    }

    /**
     * 根据id获取后台相册信息
     *
     * @param albumId 相册id
     * @return {@link Result}相册信息
     */
    @ApiOperation(value = "根据id获取后台相册信息")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @GetMapping("/info/{albumId}")
    public Result<PhotoAlbumBackDTO> getAlbumBackById(@PathVariable("albumId") Integer albumId) {
        PhotoAlbumBackDTO response = photoAlbumService.getAlbumBackById(albumId);
        return Result.ok(response);
    }

    /**
     * 保存或更新相册
     *
     * @param photoAlbumVO 相册信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新相册")
    @PostMapping("/saveOrUpdate")
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.ok();
    }
}
