package com.gmy.blog.controller;

import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.strategy.context.UploadStrategyContext;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
