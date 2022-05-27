package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.TagBackDTO;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmydl
 * @title: TagDao
 * @projectName blog
 * @description: TODO
 * @date 2022/5/27 12:17
 */
@Repository
public interface TagDao extends BaseMapper<TagEntity> {

    /**
     * 查询后台标签列表
     *
     * @param limitCurrent   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List<TagBackDTO>} 标签列表
     */
    List<TagBackDTO> getAllTagBackDTO(@Param("current") Long limitCurrent, @Param("size") Long size,
                                      @Param("condition") ConditionVO condition);

}
