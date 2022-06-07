package com.gmy.blog.controller;

import com.gmy.blog.dto.authority.MenuDTO;
import com.gmy.blog.service.MenuService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gmydl
 * @title: MenuController
 * @projectName blog-api
 * @description: 菜单（权限）模块
 * @date 2022/6/5 22:56
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 查询菜单列表
     *
     * @param conditionVO 条件
     * @return {@link Result<MenuDTO>} 菜单列表 权限
     */
    @ApiOperation(value = "查看菜单列表")
    @GetMapping("/admin/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVO conditionVO) {
        return Result.ok(menuService.listMenus(conditionVO));
    }

}
