package com.gmy.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.wallpaper.PhotoAlbumBackDTO;
import com.gmy.blog.entity.PhotoAlbumEntity;
import com.gmy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 相册映射器
 *
 * @author yezhiqiu
 * @date 2021/08/04
 */
@Repository
public interface PhotoAlbumDao extends BaseMapper<PhotoAlbumEntity> {


    /**
     * 搜索相册列表
     * @param limitCurrent 当前页
     * @param size 页大小
     * @param condition 条件
     * @return 相册集
     */
    List<PhotoAlbumBackDTO> listPhotoAlbumBacks(@Param("current") Long limitCurrent,
                                                @Param("size")Long size,
                                                @Param("condition")ConditionVO condition);
}




