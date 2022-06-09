package com.gmy.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author gmydl
 * @title: BackgroundEntity
 * @projectName blog-api
 * @description: 背景
 * @date 2022/6/9 20:42
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="tb_background")
public class BackgroundEntity {
    /**
     * 页面id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 页面名
     */
    private String backgroundName;

    /**
     * 页面标签
     */
    private String backgroundLabel;

    /**
     * 页面封面
     */
    private String backgroundCover;

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
