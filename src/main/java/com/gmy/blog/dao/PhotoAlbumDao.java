package com.gmy.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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


}




