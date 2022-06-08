package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.PhotoAlbumDao;
import com.gmy.blog.dao.PhotoDao;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumDTO;
import com.gmy.blog.dto.wallpaper.PhotoAlbumVO;
import com.gmy.blog.entity.PhotoAlbumEntity;
import com.gmy.blog.entity.PhotoEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
}
