package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.entity.PhotoAlbumEntity;

import java.util.List;

/**
 * @author gmydl
 * @title: PhotoAlbumService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 20:40
 */
public interface PhotoAlbumService extends IService<PhotoAlbumEntity>{

    /**
     * 获取所有相册列表
     * @return
     */
    List<PhotoAlbumDTO> listPhotoAlbums();

    PhotoAlbumBackDTO getAlbumBackById(Integer albumId);
}
