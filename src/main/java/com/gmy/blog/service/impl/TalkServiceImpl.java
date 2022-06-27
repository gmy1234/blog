package com.gmy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.TalkDao;
import com.gmy.blog.dto.TalkBackDTO;
import com.gmy.blog.entity.TalkEntity;
import com.gmy.blog.service.TalkService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.util.CommonUtils;
import com.gmy.blog.util.UserUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import com.gmy.blog.vo.TalkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author gmydl
 * @title: TalkServiceImpl
 * @projectName blog-api
 * @description: 说说服务
 * @date 2022/6/26 15:28
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkDao, TalkEntity> implements TalkService {

    @Autowired
    private TalkDao talkDao;

    @Override
    public PageResult<TalkBackDTO> getAllTalk(ConditionVO conditionVO) {
        // 查询说说总量
        int count = Math.toIntExact(talkDao.selectCount(new LambdaQueryWrapper<TalkEntity>()
                .eq(Objects.nonNull(conditionVO.getStatus()), TalkEntity::getStatus, conditionVO.getStatus())));
        if (count == 0) {
            return new PageResult<>();
        }

        List<TalkBackDTO> res = talkDao.getAllTalk(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);

        res.forEach(item -> {
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(res, count);
    }

    @Override
    public void saveOrUpdateTalk(TalkVO talkVO) {
        TalkEntity talkEntity = BeanCopyUtils.copyObject(talkVO, TalkEntity.class);
        talkEntity.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(talkEntity);
    }

    @Override
    public TalkBackDTO getTalkById(Integer talkId) {
        TalkBackDTO talk = talkDao.getTalkById(talkId);
        // 转换图片格式
        if (Objects.nonNull(talk.getImages())) {
            talk.setImgList(CommonUtils.castList(JSON.parseObject(talk.getImages(), List.class), String.class));
        }
        return talk;
    }

    @Override
    public void deleteTalkById(Integer talkId) {
        talkDao.deleteById(talkId);
    }
}
