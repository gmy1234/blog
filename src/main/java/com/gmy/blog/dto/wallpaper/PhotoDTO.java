package com.gmy.blog.dto.wallpaper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gmydl
 * @title: PhotoDTO
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/8 18:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {
    /**
     * 相册封面
     */
    private String photoAlbumCover;

    /**
     * 相册名
     */
    private String photoAlbumName;

    /**
     * 照片列表
     */
    private List<String> photoList;
}
