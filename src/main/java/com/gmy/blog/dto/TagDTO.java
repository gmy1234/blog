package com.gmy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: TagDTO
 * @projectName blog
 * @description: 搜索标签
 * @date 2022/5/27 14:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;
}
