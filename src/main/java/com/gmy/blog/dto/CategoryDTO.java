package com.gmy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: CategoryDTO
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/15 21:36
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类下的文章数量
     */
    private Integer articleCount;
}
