package com.gmy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: FriendLinkDTO
 * @projectName blog-api
 * @description: 前台展示
 * @date 2022/7/1 13:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkDTO {
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
     * 介绍
     */
    private String linkIntro;
}
