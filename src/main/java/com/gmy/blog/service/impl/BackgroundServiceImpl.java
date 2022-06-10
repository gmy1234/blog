package com.gmy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.BackgroundDao;
import com.gmy.blog.entity.BackgroundEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.BackgroundService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.BackgroundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.gmy.blog.constant.RedisPrefixConst.BACKGROUND;

/**
 * @author gmydl
 * @title: BackgroundServiceImpl
 * @projectName blog-api
 * @description: 背景模块
 * @date 2022/6/9 20:46
 */
@Service
public class BackgroundServiceImpl extends ServiceImpl<BackgroundDao, BackgroundEntity> implements BackgroundService {

    @Autowired
    private BackgroundDao backgroundDao;

    @Autowired
    private RedisService redisService;

    @Override
    public List<BackgroundVO> listBackground() {
        List<BackgroundVO> backgroundVOList;

        Object backgroundList = redisService.get(BACKGROUND);
        // 查redis
        if (Objects.isNull(backgroundList)){
            List<BackgroundEntity> backgroundEntities = backgroundDao.selectList(null);
            backgroundVOList = BeanCopyUtils.copyList(backgroundEntities, BackgroundVO.class);
            redisService.set(BACKGROUND, JSON.toJSONString(backgroundVOList));
        }else {
            // 序列化成对象
            backgroundVOList = JSON.parseObject(backgroundList.toString(), List.class);
        }

        return backgroundVOList;
    }

    @Override
    public void saveOrUpdateBackground(BackgroundVO backgroundVO) {
        BackgroundEntity background = BeanCopyUtils.copyObject(backgroundVO, BackgroundEntity.class);
        this.saveOrUpdate(background);
        // 删缓存
        redisService.del(BACKGROUND);
    }

    @Override
    public void deleteBackground(Integer backgroundId) {
        BackgroundEntity background = backgroundDao.selectById(backgroundId);
        if (Objects.isNull(background)){
            throw new BizException("ID 不存在");
        }
        this.baseMapper.deleteById(backgroundId);
        // 删除缓存
        redisService.del(BACKGROUND);
    }
}
