package com.gmy.blog.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: ArticleStatisticsDTO
 * @projectName blog-api
 * @description: 文章统计
 * @date 2022/6/15 21:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleStatisticsDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private Integer count;
}
