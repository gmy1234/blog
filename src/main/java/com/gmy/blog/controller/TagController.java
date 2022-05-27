package com.gmy.blog.controller;


import com.gmy.blog.service.TagService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageUtils;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin/article/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 可以查多个
     * @return
     */
    @ApiOperation(value = "条件查询带分页")
    @GetMapping("")
    public Result<?> getTagConditionPage( ){




        return Result.ok();
    }

}
