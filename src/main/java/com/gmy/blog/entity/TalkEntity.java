package com.gmy.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author gmydl
 * @title: TalkEntity
 * @projectName blog-api
 * @description: 说说
 * @date 2022/6/26 15:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_talk")
public class TalkEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 说说内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 说说状态 1.公开 2.私密
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
