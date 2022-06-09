package com.gmy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author gmydl
 * @title: PageVO
 * @projectName blog-api
 * @description: 背景
 * @date 2022/6/8 21:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "背景")
public class BackgroundVO {
    /**
     * 背景id
     */
    @ApiModelProperty(name = "id", value = "背景id", required = true, dataType = "Integer")
    private Integer id;

    /**
     * 背景名
     */
    @NotBlank(message = "背景名称不能为空")
    @ApiModelProperty(name = "pageName", value = "背景名称", required = true, dataType = "String")
    private String backgroundName;

    /**
     * 背景标签
     */
    @NotBlank(message = "背景标签不能为空")
    @ApiModelProperty(name = "pageLabel", value = "背景标签", required = true, dataType = "String")
    private String backgroundLabel;

    /**
     * 背景封面
     */
    @NotBlank(message = "背景封面不能为空")
    @ApiModelProperty(name = "pageCover", value = "背景封面", required = true, dataType = "String")
    private String backgroundCover;
}
