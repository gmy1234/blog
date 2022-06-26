package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.TalkBackDTO;
import com.gmy.blog.entity.TalkEntity;
import com.gmy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说说
 *
 * @author gmy
 *
 */
@Repository
public interface TalkDao extends BaseMapper<TalkEntity> {


    /**
     * 分页查询说说
     * @param limitCurrent 当前页
     * @param size 页大小
     * @param condition 状态
     * @return 说说列表
     */
    List<TalkBackDTO> getAllTalk(@Param("current") Long limitCurrent,
                                 @Param("size") Long size,
                                 @Param("condition") ConditionVO condition);
}




