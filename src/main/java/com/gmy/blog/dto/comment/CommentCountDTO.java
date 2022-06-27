package com.gmy.blog.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: CommentCountDTO
 * @projectName blog-api
 * @description: 评论数量
 * @date 2022/6/27 15:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCountDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 评论数量
     */
    private Integer commentCount;
}
