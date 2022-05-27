package com.gmy.blog.controller;


import com.gmy.blog.dto.TagBackDTO;
import com.gmy.blog.dto.TagDTO;
import com.gmy.blog.service.TagService;
import com.gmy.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author gmydl
 * @title: TagController
 * @projectName blog-api
 * @description: 标签模块
 * @date 2022/5/24 22:24
 */
@CrossOrigin
@Api(tags = "标签模块")
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询后台标签列表
     *
     * @param condition 条件
     * @return
     */
    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/getAll")
    public Result<PageResult<TagBackDTO>> getAllTagBackDTO(ConditionVO condition){

        PageResult<TagBackDTO> response = tagService.getAllTagBackDTO(condition);
        return Result.ok(response);
    }

    /**
     * 搜索文章标签
     *
     * @param condition 条件
     * @return {@link Result<String>} 标签列表
     */
    @ApiOperation(value = "搜索文章标签")
    @GetMapping("/search")
    public Result<List<TagDTO>> listTagsBySearch(ConditionVO condition) {
        return Result.ok(tagService.listTagsBySearch(condition));
    }

    /**
     * 批量删除标签
     * @param tagIdList 标签ID集合
     * @return
     */
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/delete")
    public Result<?> deletedTagBatch(@RequestBody List<Integer> tagIdList){
        System.out.println(tagIdList);
        return Result.ok();
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return
     */
    @ApiOperation(value = "删除标签")
    @PostMapping("/delete/{id}")
    public Result<?> deletedTag(@PathVariable("id") Integer id){
        tagService.deletedTag(id);
        return Result.ok();
    }

    /**
     * 添加或修改标签
     *
     * @param tagVO 标签信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/saveOrUpdateTag")
    public Result<?> saveOrUpdateTag(@Valid @RequestBody TagVo tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.ok();
    }

}
