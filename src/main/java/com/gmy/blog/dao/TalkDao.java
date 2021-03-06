package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.TalkBackDTO;
import com.gmy.blog.dto.TalkDTO;
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
     * 后台系统分页查询说说
     * @param limitCurrent 当前页
     * @param size 页大小
     * @param condition 状态
     * @return 说说列表
     */
    List<TalkBackDTO> getAllTalk(@Param("current") Long limitCurrent,
                                 @Param("size") Long size,
                                 @Param("condition") ConditionVO condition);

    /**
     * 根据 说说 ID 获取说说信息
     * @param talkId Id
     * @return 说说信息
     */
    TalkBackDTO getTalkById(@Param("talkId")Integer talkId);

    /**
     * 前台分页查说说列表
     * @param current 当前页
     * @param size 页大小
     * @return 说说列表
     */
    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);

    /**
     *
     * @param talkId ss ID
     * @return
     */
    TalkDTO obtainTalkById(@Param("talkId")Integer talkId);
}




