package com.gmy.blog.dto.authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gmydl
 * @title: LabelOptionDTO
 * @projectName blog-api
 * @description: 标签选项
 * @date 2022/6/6 23:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelOptionDTO {

    /**
     * 选项id
     */
    private Integer id;

    /**
     * 选项名
     */
    private String label;

    /**
     * 子选项
     */
    private List<LabelOptionDTO> children;
}
