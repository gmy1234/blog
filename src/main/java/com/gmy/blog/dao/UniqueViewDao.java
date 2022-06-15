package com.gmy.blog.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.UniqueViewDTO;
import com.gmy.blog.entity.UniqueViewEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 访问量
 *
 * @author xiaojie
 * @date 2021/08/10
 * @since 2020-05-18
 */
@Repository
public interface UniqueViewDao extends BaseMapper<UniqueViewEntity> {


    /**
     * 获取7天用户量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews(@Param("startTime")DateTime startTime,
                                        @Param("endTime")DateTime endTime);
}
