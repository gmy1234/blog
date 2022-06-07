package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.constant.RedisPrefixConst;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.util.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.gmy.blog.constant.CommonConst.*;
import static com.gmy.blog.constant.RedisPrefixConst.UNIQUE_VISITOR;
import static com.gmy.blog.constant.RedisPrefixConst.VISITOR_AREA;

/**
 * @author gmydl
 * @title: BlogInfoServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 14:49
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisService redisService;

    @Override
    public void reportVisitorInfo() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());

        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }

        }
        // 访问量+1
        redisService.incr(RedisPrefixConst.BLOG_VIEWS_COUNT, 1);
        // 保存唯一标识
        redisService.sAdd(UNIQUE_VISITOR, md5);


    }
}
