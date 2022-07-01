package com.gmy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author gmydl
 * @title: FriendLinkBackDTO
 * @projectName blog-api
 * @description: 后台友连接 友情链接
 * @date 2022/7/1 13:43
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkBackDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 链接介绍
     */
    private String linkIntro;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
