package com.gmy.blog.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gmydl
 * @title: TalkBackDTO
 * @projectName blog-api
 * @description: 后台说说列表返回对象
 * @date 2022/6/26 15:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalkBackDTO {


    /**
     * 说说id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    private String avatar;

    /**
     * 说说内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 图片列表
     */
    private List<String> imgList;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
