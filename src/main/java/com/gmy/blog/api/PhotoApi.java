package com.gmy.blog.api;

import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.dto.wallpaper.PhotoDTO;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.service.PhotoService;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gmydl
 * @title: PhotoApi
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/8 17:56
 */
@RestController
@RequestMapping("/wallpaper")
public class PhotoApi {

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Autowired
    private PhotoService photoService;

    /**
     * 获取相册列表( PS: 和后台接口实现一摸一样)
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表
     */
    @ApiOperation(value = "获取相册列表")
    @GetMapping("/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.ok(photoAlbumService.listPhotoAlbums());
    }

    /**
     * 根据相册id查看照片列表
     *
     * @return {@link Result<PhotoAlbumDTO>} 照片列表
     */
    @ApiOperation(value = "获取照片列表")
    @GetMapping("/albums/{albumId}")
    public Result<PhotoDTO> getPhotos(@PathVariable("albumId") Integer albumId) {
        PhotoDTO response = photoAlbumService.getPhotos(albumId);
    return Result.ok(response);
    }
}
