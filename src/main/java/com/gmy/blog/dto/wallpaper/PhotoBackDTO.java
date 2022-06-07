package com.gmy.blog.dto.wallpaper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: PhotoBackDTO
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/7 20:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoBackDTO {
    /**
     * 照片id
     */
    private Integer id;

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

}
