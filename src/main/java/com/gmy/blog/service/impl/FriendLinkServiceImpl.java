package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.FriendLinkDao;
import com.gmy.blog.dto.FriendLinkBackDTO;
import com.gmy.blog.dto.FriendLinkDTO;
import com.gmy.blog.entity.FriendLinkEntity;
import com.gmy.blog.service.FriendLinkService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.FriendLinkVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gmydl
 * @title: FriendLinkServiceImpl
 * @projectName blog-api
 * @description: 友连
 * @date 2022/7/1 13:35
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkDao, FriendLinkEntity> implements FriendLinkService {

    @Autowired
    private FriendLinkDao friendLinkDao;

    @Override
    public PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO condition) {
        // 分页查询
        Page<FriendLinkEntity> page = new Page(PageUtils.getLimitCurrent(), PageUtils.getSize());
        LambdaQueryWrapper<FriendLinkEntity> wrapper = new LambdaQueryWrapper<>();
        if (condition.getKeywords() != null) {
            wrapper.like(FriendLinkEntity::getLinkName, condition.getKeywords());
        }

        Page<FriendLinkEntity> friendLinkPage = friendLinkDao.selectPage(page, wrapper);
        List<FriendLinkBackDTO> friendLinkBackList = BeanCopyUtils.copyList(friendLinkPage.getRecords(), FriendLinkBackDTO.class);

        return new PageResult<>(friendLinkBackList , (int) friendLinkPage.getTotal());
    }

    @Override
    public void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO) {
        FriendLinkEntity friendLink = BeanCopyUtils.copyObject(friendLinkVO, FriendLinkEntity.class);
        this.saveOrUpdate(friendLink);
    }

    @Override
    public List<FriendLinkDTO> listFriendLinks() {
        // 查询友链列表
        List<FriendLinkEntity> friendLinkList = friendLinkDao.selectList(null);
        return BeanCopyUtils.copyList(friendLinkList, FriendLinkDTO.class);
    }
}
