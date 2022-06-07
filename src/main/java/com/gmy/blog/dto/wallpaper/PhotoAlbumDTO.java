package com.gmy.blog.dto.wallpaper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: PhotoAlbumDTO
 * @projectName blog-api
 * @description: 相册
 * @date 2022/6/7 20:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumDTO {
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
}
