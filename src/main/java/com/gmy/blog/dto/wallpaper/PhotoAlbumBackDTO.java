package com.gmy.blog.dto.wallpaper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: PhotoAlbumBackDTO
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/8 00:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumBackDTO {
    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 相册封面
     */
    private String albumCover;

    /**
     * 照片数量
     */
    private Integer photoCount;

    /**
     * 状态值 1公开 2私密
     */
    private Integer status;
}
