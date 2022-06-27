package com.gmy.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.TalkBackDTO;
import com.gmy.blog.dto.TalkDTO;
import com.gmy.blog.entity.TalkEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.TalkVO;

import java.util.List;

/**
 * 说说
 *
 * @author gmy
 * @date 2022/06/23
 */
public interface TalkService extends IService<TalkEntity> {


    /**
     * 后台分页获取说说列表
     * @return 说说列表
     */
    PageResult<TalkBackDTO> getAllTalk(ConditionVO conditionVO);

    /**
     * 保存或者修改说说
     * @param talkVO 说说信息
     */
    void saveOrUpdateTalk(TalkVO talkVO);

    /**
     * 根据ID 查看说说
     * @param talkId 说说ID
     */
    TalkBackDTO getTalkById(Integer talkId);

    /**
     * 删除说说
     * @param talkId 说说 ID
     */
    void deleteTalkById(Integer talkId);

    /**
     * 前台 查询最新的10条说说
     * @return 说说内容
     */
    List<String> listHomeTalks();

    /**
     * 前台 查看说说列表
     * @return 说说信息
     */
    PageResult<TalkDTO> listTalk();

    /**
     * 前台获取根据 说说 ID 获取说说
     * @param talkId 说说 ID
     * @return 获取说说
     */
    TalkDTO obtainTalkById(Integer talkId);
}
