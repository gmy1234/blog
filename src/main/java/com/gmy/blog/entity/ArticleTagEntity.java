package com.gmy.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author gmydl
 * @title: ArticleTag
 * @projectName blog-api
 * @description: 文章标签关联表
 * @date 2022/5/2421:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article_tag")
public class ArticleTagEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 标签id
     */
    private Integer tagId;

}
