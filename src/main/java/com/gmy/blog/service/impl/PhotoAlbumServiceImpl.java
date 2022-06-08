package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.PhotoAlbumDao;
import com.gmy.blog.dao.PhotoDao;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.dto.wallpaper.PhotoDTO;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.vo.PhotoAlbumVO;
import com.gmy.blog.entity.PhotoAlbumEntity;
import com.gmy.blog.entity.PhotoEntity;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.FALSE;
import static com.gmy.blog.constant.CommonConst.TRUE;
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

    @Override
    public PhotoAlbumBackDTO getAlbumBackById(Integer albumId) {

        PhotoAlbumEntity albumInfo = photoAlbumDao.selectById(albumId);
        PhotoAlbumBackDTO albumBackData = BeanCopyUtils.copyObject(albumInfo, PhotoAlbumBackDTO.class);
        // 查询照片的数量
        Long photoCount = photoDao.selectCount(new LambdaQueryWrapper<PhotoEntity>()
                .eq(PhotoEntity::getAlbumId, albumId)
                .eq(PhotoEntity::getIsDelete, FALSE));
        albumBackData.setPhotoCount(Math.toIntExact(photoCount));
        return albumBackData;
    }

    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        // 查询相册名
//        String albumName = photoAlbumVO.getAlbumName();
//        PhotoAlbumEntity album = photoAlbumDao.selectOne(new LambdaQueryWrapper<PhotoAlbumEntity>()
//                .select(PhotoAlbumEntity::getId)
//                .eq(PhotoAlbumEntity::getAlbumName, albumName));
//
//        if (Objects.nonNull(album) && album.getId().equals(photoAlbumVO.getId())){
//            throw new BizException("相册名已经存在");
//        }
        PhotoAlbumEntity responseData = BeanCopyUtils.copyObject(photoAlbumVO, PhotoAlbumEntity.class);
        this.saveOrUpdate(responseData);

    }

    @Override
    public PageResult<PhotoAlbumBackDTO> searchAlbums(ConditionVO condition) {
        // 查询相册数量
        Long count = photoAlbumDao.selectCount(new LambdaQueryWrapper<PhotoAlbumEntity>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), PhotoAlbumEntity::getAlbumName, condition.getKeywords())
                .eq(PhotoAlbumEntity::getIsDelete, FALSE));

        if (count == 0) {
            return new PageResult<>();
        }
        // 查询相册信息
        List<PhotoAlbumBackDTO> photoAlbumBackList = photoAlbumDao.listPhotoAlbumBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<PhotoAlbumBackDTO>(photoAlbumBackList, Math.toIntExact(count));
    }

    @Override
    public void deleteAlbum(Integer albumId) {
        Long photoCount = photoDao.selectCount(new LambdaQueryWrapper<PhotoEntity>()
                .eq(PhotoEntity::getAlbumId, albumId));
        // 相册里的照片数量  >  0
        if (photoCount > 0) {
            // 逻辑删除相册里的照片
            photoAlbumDao.updateById(PhotoAlbumEntity.builder()
                    .id(albumId)
                    .isDelete(TRUE)
                    .build());
            photoDao.update(new PhotoEntity(), new LambdaUpdateWrapper<PhotoEntity>()
                    .set(PhotoEntity::getIsDelete, TRUE)
                    .eq(PhotoEntity::getAlbumId, albumId));
        }else {
            // 直接删除 相册
            photoAlbumDao.deleteById(albumId);
        }
    }

    /**
     * 查根据 相册ID 查图片列表
     * @param albumId 相册 ID
     * @return 图片集
     */
    @Override
    public PhotoDTO getPhotos(Integer albumId) {
        PhotoDTO responseData = new PhotoDTO();
        PhotoAlbumEntity album = photoAlbumDao.selectOne(new LambdaQueryWrapper<PhotoAlbumEntity>()
                .eq(PhotoAlbumEntity::getId, albumId)
                .eq(PhotoAlbumEntity::getStatus, PUBLIC.getStatus())
                .eq(PhotoAlbumEntity::getIsDelete, FALSE));
        if (Objects.isNull(album)){
            throw new BizException("相册不存在");
        }
        responseData.setPhotoAlbumName(album.getAlbumName());
        responseData.setPhotoAlbumCover(album.getAlbumCover());

        // 分页查图片列表
        Page<PhotoEntity> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());

        // 分页查询图片地址
        List<PhotoEntity> photos = photoDao.selectPage(page, new LambdaQueryWrapper<PhotoEntity>()
                        .select(PhotoEntity::getPhotoSrc)
                        .eq(PhotoEntity::getAlbumId, albumId)
                        .eq(PhotoEntity::getIsDelete, false)
                        .orderByDesc(PhotoEntity::getId))
                .getRecords();
        List<String> photosSrc = photos.stream().
                map(PhotoEntity::getPhotoSrc)
                .collect(Collectors.toList());

        responseData.setPhotoList(photosSrc);
        return responseData;
    }
}
