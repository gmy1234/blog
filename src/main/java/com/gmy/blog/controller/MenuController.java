package com.gmy.blog.controller;

import com.gmy.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
}
