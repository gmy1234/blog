package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.PhotoAlbumDao;
import com.gmy.blog.dao.PhotoDao;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.entity.PhotoAlbumEntity;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gmy.blog.constant.CommonConst.FALSE;
import static com.gmy.blog.enums.PhotoAlbumStatusEnum.PUBLIC;

/**
 * @author gmydl
 * @title: PhotoAlbumServiceImpl
 * @projectName blog-api
 * @description: 相册服务
 * @date 2022/6/7 20:40
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumDao, PhotoAlbumEntity> implements PhotoAlbumService {

    @Autowired
    private PhotoAlbumDao photoAlbumDao;
    @Autowired
    private PhotoDao photoDao;

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbums() {
        // 查询相册列表
        List<PhotoAlbumEntity> photoAlbumList = photoAlbumDao.selectList(new LambdaQueryWrapper<PhotoAlbumEntity>()
                .eq(PhotoAlbumEntity::getStatus, PUBLIC.getStatus())
                .eq(PhotoAlbumEntity::getIsDelete, FALSE)
                .orderByDesc(PhotoAlbumEntity::getId));
        return BeanCopyUtils.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }
}
