package com.gmy.blog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gmydl
 * @title: UserAreaDTO
 * @projectName blog-api
 * @description: 用户地区
 * @date 2022/6/15 20:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAreaDTO {
    /**
     * 地区名
     */
    private String name;

    /**
     * 数量
     */
    private Long value;
}
