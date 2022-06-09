package com.gmy.blog.controller;

import com.gmy.blog.annotation.OptLog;
import com.gmy.blog.service.BackgroundService;
import com.gmy.blog.vo.BackgroundVO;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.gmy.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * @author gmydl
 * @title: BackgroundController
 * @projectName blog-api
 * @description: 背景模块
 * @date 2022/6/9 20:48
 */
@RestController
@RequestMapping("/admin/background")
public class BackgroundController {

    @Autowired
    private BackgroundService backgroundService;

    /**
     * 获取背景列表
     *
     * @return {@link Result< BackgroundVO >}
     */
    @ApiOperation(value = "获取背景列表")
    @GetMapping("/list")
    public Result<List<BackgroundVO>> listBackground() {
        List<BackgroundVO> resData = backgroundService.listBackground();
        return Result.ok(resData);
    }

    /**
     * 保存或更新页面
     *
     * @param backgroundVO 页面信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新背景")
    @PostMapping("/update")
    public Result<?> saveOrUpdatePage(@Valid @RequestBody BackgroundVO backgroundVO) {
        backgroundService.saveOrUpdateBackground(backgroundVO);
        return Result.ok();
    }

    /**
     * 删除背景
     *
     * @param backgroundId 背景 ID
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除")
    @GetMapping("/delete/{backgroundId}")
    public Result<?> deleteBackground(@PathVariable("backgroundId") Integer backgroundId) {
        backgroundService.deleteBackground(backgroundId);
        return Result.ok();
    }
}
