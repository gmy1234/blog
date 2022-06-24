package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.PhotoDao;
import com.gmy.blog.dto.wallpaper.PhotoBackDTO;
import com.gmy.blog.entity.PhotoAlbumEntity;
import com.gmy.blog.vo.*;
import com.gmy.blog.entity.PhotoEntity;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.service.PhotoService;
import com.gmy.blog.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.FALSE;

/**
 * @author gmydl
 * @title: PhotoServiceImpl
 * @projectName blog-api
 * @description: 照片服务
 * @date 2022/6/7 20:39
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoDao, PhotoEntity> implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Override
    public PageResult<PhotoBackDTO> listPhotos(ConditionVO condition) {
        // 查询照片列表
        Page<PhotoEntity> page = new Page(PageUtils.getCurrent(), PageUtils.getSize());
        Page<PhotoEntity> photoPage = photoDao.selectPage(page, new LambdaQueryWrapper<PhotoEntity>()
                .eq(Objects.nonNull(condition.getAlbumId()), PhotoEntity::getAlbumId, condition.getAlbumId())
                .eq(PhotoEntity::getIsDelete, condition.getIsDelete())
                .orderByDesc(PhotoEntity::getId)
                .orderByDesc(PhotoEntity::getUpdateTime));

        List<PhotoBackDTO> photoList = BeanCopyUtils.copyList(photoPage.getRecords(), PhotoBackDTO.class);
        return new PageResult<PhotoBackDTO>(photoList, (int) photoPage.getTotal());
    }


    @Override
    public void uploadPhotos(PhotoVO photoVo) {
        List<PhotoEntity> photoList = photoVo.getPhotoUrlList().stream().map(item ->
            PhotoEntity.builder()
                    .albumId(photoVo.getAlbumId())
                    .photoName(IdWorker.getIdStr())
                    .photoSrc(item)
                    .build()
        ).collect(Collectors.toList());

        this.saveBatch(photoList);
    }

    @Override
    public void moveOtherAlbum(PhotoVO photoVO) {
        // 获取指定到 相册ID
        Integer albumId = photoVO.getAlbumId();

        List<Integer> photoIdList = photoVO.getPhotoIdList();
        List<PhotoEntity> photosData = photoIdList.stream().map(item -> PhotoEntity.builder()
                        .albumId(albumId)
                        .id(item)
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photosData);
    }

    @Override
    public void updatePhoto(DeleteVo deleteVo) {
        // 更新照片状态
        List<PhotoEntity> photoList = deleteVo.getIdList().stream().map(item -> PhotoEntity.builder()
                        .id(item)
                        .isDelete(deleteVo.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
        // 若恢复照片所在的相册已删除，恢复相册
        if (deleteVo.getIsDelete().equals(FALSE)) {
            List<PhotoAlbumEntity> photoAlbumList = photoDao.selectList(new LambdaQueryWrapper<PhotoEntity>()
                            .select(PhotoEntity::getAlbumId)
                            .in(PhotoEntity::getId, deleteVo.getIdList())
                            .groupBy(PhotoEntity::getAlbumId))
                    .stream()
                    .map(item -> PhotoAlbumEntity.builder()
                            .id(item.getAlbumId())
                            .isDelete(FALSE)
                            .build())
                    .collect(Collectors.toList());
            photoAlbumService.updateBatchById(photoAlbumList);
        }
    }
}
