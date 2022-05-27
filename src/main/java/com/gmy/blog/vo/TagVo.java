package com.gmy.blog.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author gmydl
 * @title: TagVo
 * @projectName blog
 * @description: TODO
 * @date 2022/5/27 12:02
 */
public class TagVo {
    /**
     * 标签id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Integer tagId;
}
