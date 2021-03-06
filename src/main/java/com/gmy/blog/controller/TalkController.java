package com.gmy.blog.controller;

import com.gmy.blog.dto.TalkBackDTO;
import com.gmy.blog.service.TalkService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.TalkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author gmydl
 * @title: TalkController
 * @projectName blog-api
 * @description: 说说
 * @date 2022/6/26 15:29
 */
@Api(tags = "说说模块")
@RestController
@RequestMapping("/admin/talk")
public class TalkController {

    @Autowired
    private TalkService talkService;

    /**
     * 获取所有的说说（分页）
     * @return 说说
     */
    @RequestMapping("/list")
    public Result<PageResult<TalkBackDTO>> getAllTalk(ConditionVO conditionVO){
        PageResult<TalkBackDTO> res = talkService.getAllTalk(conditionVO);
        return Result.ok(res);
    }


    /**
     * 保存或修改说说
     *
     * @param talkVO 说说信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "保存或修改说说")
    @PostMapping("/saveOrUpdate")
    public Result<?> saveOrUpdateTalk(@Valid @RequestBody TalkVO talkVO) {
        talkService.saveOrUpdateTalk(talkVO);
        return Result.ok();
    }

    /**
     * 根据ID查看说说
     *
     * @param talkId 说说Id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "根据ID查看说说")
    @GetMapping("/{talkId}")
    public Result<TalkBackDTO> getTalkById(@PathVariable("talkId") Integer talkId) {
        TalkBackDTO res = talkService.getTalkById(talkId);
        return Result.ok(res);
    }


    /**
     * 删除说说
     *
     * @param talkId 说说Id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "根据ID查看说说")
    @PostMapping("/delete/{talkId}")
    public Result<?> deleteTalkById(@PathVariable("talkId") Integer talkId) {
         talkService.deleteTalkById(talkId);
        return Result.ok();
    }
}
