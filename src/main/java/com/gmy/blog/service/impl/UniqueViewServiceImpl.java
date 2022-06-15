package com.gmy.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UniqueViewDao;
import com.gmy.blog.dto.UniqueViewDTO;
import com.gmy.blog.entity.UniqueViewEntity;

import com.gmy.blog.service.RedisService;
import com.gmy.blog.service.UniqueViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 访问量统计服务
 *
 * @author gmy
 * @date 2021/08/06
 */
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewDao, UniqueViewEntity> implements UniqueViewService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UniqueViewDao uniqueViewDao;


    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return uniqueViewDao.listUniqueViews(startTime, endTime);
    }
}
