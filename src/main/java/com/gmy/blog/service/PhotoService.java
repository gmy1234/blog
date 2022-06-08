package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.wallpaper.PhotoBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoVO;
import com.gmy.blog.entity.PhotoEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;

/**
 * @author gmydl
 * @title: PhotoService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 20:39
 */
public interface PhotoService extends IService<PhotoEntity> {

    /**
     * 根据相册id获取照片列表
     *
     * @param condition 条件
     * @return {@link PageResult<PhotoBackDTO>} 照片列表
     */
    PageResult<PhotoBackDTO> listPhotos(ConditionVO condition);

    /**
     * 上传照片
     * @param photoVo 照片的信息
     */
    void uploadPhotos(PhotoVO photoVo);
}
