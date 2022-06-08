package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.dto.wallpaper.PhotoDTO;
import com.gmy.blog.vo.PhotoAlbumVO;
import com.gmy.blog.entity.PhotoAlbumEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;

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

    /**
     * 搜索后台相册列表
     *
     * @param condition 条件
     * @return {@link PageResult<PhotoAlbumBackDTO>} 相册列表
     */
    PageResult<PhotoAlbumBackDTO> searchAlbums(ConditionVO condition);

    /**
     * 删除相册
     * @param albumId 相册ID
     */
    void deleteAlbum(Integer albumId);

    /**
     * 根据 相册 ID 获取图片列表
     * @param albumId 相册 ID
     * @return 图片
     */
    PhotoDTO getPhotos(Integer albumId);
}
