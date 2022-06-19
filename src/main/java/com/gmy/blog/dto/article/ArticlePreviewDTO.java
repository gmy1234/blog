package com.gmy.blog.dto.article;

import com.gmy.blog.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author gmydl
 * @title: ArticlePreviewDTO
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/19 15:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePreviewDTO {

    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 文章分类id
     */
    private Integer categoryId;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;
}
