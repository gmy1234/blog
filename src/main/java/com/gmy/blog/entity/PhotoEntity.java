package com.gmy.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author gmydl
 * @title: PhotoEntity
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 20:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value ="tb_photo")
public class PhotoEntity {
    /**
     * 照片id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 相册id
     */
    private Integer albumId;

    /**
     * 照片名
     */
    private String photoName;

    /**
     * 照片描述
     */
    private String photoDesc;

    /**
     * 照片地址
     */
    private String photoSrc;

    /**
     * 是否删除
     */
    private Integer isDelete;

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
