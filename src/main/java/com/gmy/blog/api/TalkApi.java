package com.gmy.blog.api;

import com.gmy.blog.dto.TalkDTO;
import com.gmy.blog.service.TalkService;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gmydl
 * @title: TalkApi
 * @projectName blog-api
 * @description: 前台说说模块请求
 * @date 2022/6/27 14:00
 */

@RestController
@RequestMapping("/talk")
public class TalkApi {

    @Autowired
    private TalkService talkService;

    /**
     * 查看首页说说
     *
     * @return {@link Result<String>}
     */
    @ApiOperation(value = "查看首页说说")
    @GetMapping("/home")
    public Result<List<String>> listHomeTalks() {
        List<String> res = talkService.listHomeTalks();
        return Result.ok(res);
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<String>}
     */
    @ApiOperation(value = "查看说说列表")
    @GetMapping("/list")
    public Result<PageResult<TalkDTO>> listTalks() {
        PageResult<TalkDTO> res = talkService.listTalk();
        return Result.ok(res);
    }


    /**
     * 根据id查看说说
     *
     * @param talkId 说说id
     * @return {@link Result<TalkDTO>}
     */
    @ApiOperation(value = "根据id查看说说")
    @ApiImplicitParam(name = "talkId", value = "说说id", required = true, dataType = "Integer")
    @GetMapping("/{talkId}")
    public Result<TalkDTO> obtainTalkById(@PathVariable("talkId") Integer talkId) {
        TalkDTO res = talkService.obtainTalkById(talkId);
        return Result.ok(res);
    }
}
