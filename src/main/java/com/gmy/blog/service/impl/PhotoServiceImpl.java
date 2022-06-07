package com.gmy.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.PhotoDao;
import com.gmy.blog.dto.wallpaper.PhotoBackDTO;
import com.gmy.blog.entity.PhotoEntity;
import com.gmy.blog.service.PhotoAlbumService;
import com.gmy.blog.service.PhotoService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return new PageResult<PhotoBackDTO>(photoList, (int)photoPage.getTotal());
    }
}
