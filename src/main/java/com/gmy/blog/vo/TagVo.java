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
 * @title: TagVo
 * @projectName blog
 * @description: 标签对象
 * @date 2022/5/27 12:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "标签对象")
public class TagVo {

    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "标签id", dataType = "Integer")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空")
    @ApiModelProperty(name = "categoryName", value = "标签名", required = true, dataType = "String")
    private String tagName;
}
