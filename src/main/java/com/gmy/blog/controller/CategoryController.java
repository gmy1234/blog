package com.gmy.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gmy.blog.dto.CategoryBackDTO;
import com.gmy.blog.dto.CategoryOptionDTO;

import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.service.CategoryService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gmydl
 * @title: CategoryController
 * @projectName blog-api
 * @description: 分类API
 * @date 2022/5/24 22:00
 */

@Api(tags = "分类模块")
@RestController
@RequestMapping("/article/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/test")
    public List<String> test() {
        ArrayList<String> data = new ArrayList<>();
        data.add("gmy");

        return data;
    }

    /**
     * @return
     */
    @ApiOperation("查看后台分类列表")
    @GetMapping("/getAll")
    public List<CategoryEntity> getAllCategory() {
        return categoryService.getAllCategory();
    }

    /**
     * 搜索文章分类
     */
    @ApiOperation("搜索文章分类")
    @GetMapping("/search")
    public List<CategoryOptionDTO> searchArticleCategory() {
        return categoryService.searchArticleCategory(new ConditionVO());
    }
}
