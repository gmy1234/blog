package com.gmy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.CategoryDao;
import com.gmy.blog.dao.TagDao;
import com.gmy.blog.dao.WebsiteConfigDao;
import com.gmy.blog.dto.BlogHomeInfoDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.entity.WebsiteConfigEntity;
import com.gmy.blog.service.BackgroundService;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.util.IpUtils;
import com.gmy.blog.vo.BackgroundVO;
import com.gmy.blog.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.gmy.blog.constant.CommonConst.*;
import static com.gmy.blog.constant.RedisPrefixConst.*;
import static com.gmy.blog.enums.PhotoAlbumStatusEnum.PUBLIC;

/**
 * @author gmydl
 * @title: BlogInfoServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 14:49
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {


    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagDao tagDao;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisService redisService;

    @Autowired
    private WebsiteConfigDao websiteConfigDao;

    @Autowired
    private BackgroundService backgroundService;

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
        redisService.incr(BLOG_VIEWS_COUNT, 1);
        // 保存唯一标识
        redisService.sAdd(UNIQUE_VISITOR, md5);
    }


    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 查询文章数量
        Long articleCount = articleDao.selectCount(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, PUBLIC.getStatus())
                .eq(ArticleEntity::getIsDelete, FALSE));
        // 查询分类数量
        Long categoryCount = categoryDao.selectCount(null);
        // 查询标签数量
        Long tagCount = tagDao.selectCount(null);
        // 查询网站访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewCount = Optional.ofNullable(count).orElse(0).toString();

        // 查询网站配置
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();

        // TODO:查询 页面信息
        List<BackgroundVO> backgrounds = backgroundService.listBackground();

        // 封装
        return BlogHomeInfoDTO.builder()
                .articleCount(Math.toIntExact(articleCount))
                .categoryCount(Math.toIntExact(categoryCount))
                .websiteConfig(websiteConfig) // 网站信息
                .viewsCount(viewCount)
                .tagCount(Math.toIntExact(tagCount))
                .backgroundList(backgrounds)
                .build();
    }


    /**
     * 查询网站信息
     *
     * @return 网站信息
     */
    public WebsiteConfigVO getWebsiteConfig() {

        WebsiteConfigVO websiteConfigVO;
        // 获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改网站配置
        WebsiteConfigEntity websiteConfig = WebsiteConfigEntity.builder()
                .id(1)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigDao.updateById(websiteConfig);
        // 删除缓存
        redisService.del(WEBSITE_CONFIG);
    }
}
