package com.gmy.blog.Scheduler;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.dao.UniqueViewDao;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dto.user.UserAreaDTO;
import com.gmy.blog.entity.UniqueViewEntity;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.*;
import static com.gmy.blog.constant.RedisPrefixConst.*;
import static com.gmy.blog.enums.ZoneEnum.SHANGHAI;

/**
 * @author gmydl
 * @title: statisticsUser
 * @projectName blog-api
 * @description: 定时统计用户数据
 * @date 2022/6/18 00:18
 */
@Component
@EnableTransactionManagement
public class statisticsUser {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private UniqueViewDao uniqueViewDao;

    @Autowired
    private RedisService redisService;


    /**
     * 统计用户地区
     */
    @Scheduled(cron = "0 30 * * * ?")
    public void statisticalUserArea() {
        // 统计用户地域分布
        Map<String, Long> userAreaMap = userAuthDao.selectList(new LambdaQueryWrapper<UserAuthEntity>().select(UserAuthEntity::getIpSource))
                .stream()
                .map(item -> {
                    if (StringUtils.isNotBlank(item.getIpSource())) {
                        return item.getIpSource().substring(0, 2)
                                .replaceAll(PROVINCE, "")
                                .replaceAll(CITY, "");
                    }
                    return UNKNOWN;
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        // 转换格式
        List<UserAreaDTO> userAreaList = userAreaMap.entrySet().stream()
                .map(item -> UserAreaDTO.builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());
        redisService.set(USER_AREA, JSON.toJSONString(userAreaList));
    }


    /**
     * 统计访客数量
     */
    // 每天0点0分执行一次
    @Scheduled(cron = " 0 0 0 * * ?", zone = "Asia/Shanghai")
    public void saveUniqueView() {
        System.out.println("统计用户访问量");
        // 获取每天用户量
        Long count = redisService.sSize(UNIQUE_VISITOR);
        // 获取昨天日期插入数据
        UniqueViewEntity uniqueView = UniqueViewEntity.builder()
                .createTime(LocalDateTimeUtil.offset(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())), -1, ChronoUnit.DAYS))
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        uniqueViewDao.insert(uniqueView);
    }

    // 每天凌晨0点2分执行
    @Scheduled(cron = " 0 2 0 * * ?", zone = "Asia/Shanghai")
    public void clear() {
        // 清空redis访客记录
        redisService.del(UNIQUE_VISITOR);
        // 清空redis游客区域统计
        redisService.del(VISITOR_AREA);
    }
}
