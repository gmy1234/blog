package com.gmy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gmy.blog.dao.*;
import com.gmy.blog.dto.*;
import com.gmy.blog.dto.article.ArticleRankDTO;
import com.gmy.blog.dto.article.ArticleStatisticsDTO;
import com.gmy.blog.dto.user.UserInfoDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.entity.WebsiteConfigEntity;
import com.gmy.blog.service.BackgroundService;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.service.UniqueViewService;
import com.gmy.blog.util.BeanCopyUtils;
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

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UniqueViewService uniqueViewService;

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

        // 背景信息
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

    /**
     * 查询文章排行
     * @param articleMap 文章信息
     * @return List<ArticleRankDTO>} 文章排行
     */
    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleDao.selectList(new LambdaQueryWrapper<ArticleEntity>()
                        .select(ArticleEntity::getId, ArticleEntity::getArticleTitle)
                        .in(ArticleEntity::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public BlogBackInfoDTO getBlogBasicInfo() {

        // 查访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewCount = Optional.ofNullable(count).orElse(0).toString();

        // TODO：查询留言量

        // 查询用户量
        Integer userCount = Math.toIntExact(userInfoDao.selectCount(null));
        // 查询文章量
        Long articleCount = articleDao.selectCount(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getIsDelete, FALSE));
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewList =  uniqueViewService.listUniqueViews();
        // 查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsList = articleDao.listArticleStatistics();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryDao.listCategoryDTO();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagDao.selectList(null), TagDTO.class);
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);

        // 封装
        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .articleCount(Math.toIntExact(articleCount))
                .articleStatisticsList(articleStatisticsList)
                .tagDTOList(tagDTOList)
                .viewsCount(Integer.parseInt(viewCount))
                .messageCount(0)
                .userCount(userCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewList)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            // 查文章排行
            List<ArticleRankDTO> articleRankDTOList = this.listArticleRank(articleMap);
            blogBackInfoDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return blogBackInfoDTO;
    }


}


