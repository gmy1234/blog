package com.gmy.blog.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author gmydl
 * @title: UserRoleVO
 * @projectName blog-api
 * @description: TODO
 * @date 2022/7/1 11:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVO {

    /**
     * userId
     */
    @NotNull(message = "id不能为空")
    private Integer userInfoId;

    /**
     * 用户名
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 用户角色id
     */
    @NotNull(message = "用户角色不能为空")
    private List<Integer> roleIdList;
}
