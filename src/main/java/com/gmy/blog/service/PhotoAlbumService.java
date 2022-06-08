package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumVO;
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

    /**
     * 根据相册 ID 获取相册信息
     * @param albumId 相册 ID
     * @return 相册信息
     */
    PhotoAlbumBackDTO getAlbumBackById(Integer albumId);

    /**
     * 添加或者修改相册信息
     * @param photoAlbumVO 相册信息
     */
    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);
}
