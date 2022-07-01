package com.gmy.blog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.FriendLinkBackDTO;
import com.gmy.blog.dto.FriendLinkDTO;
import com.gmy.blog.entity.FriendLinkEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.FriendLinkVO;
import com.gmy.blog.vo.PageResult;

import java.util.List;


/**
 * 友链服务
 *
 * @author gmy
 * @date 2022/07/01
 */
public interface FriendLinkService extends IService<FriendLinkEntity> {


    PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO condition);

    /**
     * 保存或者修改友联
     * @param friendLinkVO 友链信息
     */
    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

    /**
     * 前台页面展示友链列表
     * @return 信息
     */
    List<FriendLinkDTO> listFriendLinks();

}
