package com.gmy.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gmy.blog.dto.CategoryBackDTO;
import com.gmy.blog.dto.CategoryOptionDTO;

import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.service.CategoryService;
import com.gmy.blog.vo.CategoryVO;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author gmydl
 * @title: CategoryController
 * @projectName blog-api
 * @description: 分类API
 * @date 2022/5/24 22:00
 */
@CrossOrigin
@Api(tags = "分类模块")
@RestController
@RequestMapping("/admin/article/category")
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
    public Result<PageResult<CategoryBackDTO>> getAllCategory(ConditionVO condition) {

          return Result.ok(categoryService.getAllCategory(condition));
    }

    /**
     * 搜索文章分类
     */
    @ApiOperation("搜索文章分类")
    @GetMapping("/search")
    public Result<List<CategoryOptionDTO>> searchArticleCategory(ConditionVO condition) {

        List<CategoryOptionDTO> category = categoryService.searchArticleCategory(condition);
        return Result.ok(category);
    }


    /**
     * 添加或者修改分类
     * @return
     */
    @ApiOperation("添加或修改分类")
    @PostMapping("/saveAndUpdateCategory")
    public Result<?> saveAndUpdateCategory(@RequestBody CategoryVO categoryVO) {
        System.out.println(categoryVO);
        categoryService.saveAndUpdateCategory(categoryVO);
        return Result.ok();
    }

    /**
     * 删除分类
     * @return
     */
    @ApiOperation("删除分类")
    @PostMapping ("/deleted/{id}")
    public Result<?> deletedCategory(@PathVariable("id") Integer id) {
        categoryService.deletedCategory(id);
        return Result.ok();
    }

    /**
     * 批量删除分类
     * @return
     */
    @ApiOperation("批量删除分类")
    @DeleteMapping ("/deleteBatch")
    public Result<?> deletedCategory(@RequestBody String ids) {
        // TODO
        System.out.println(ids);
        //categoryService.deletedCategoryBatch(ids);
        return Result.ok();
    }
}
