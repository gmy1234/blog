package com.gmy.blog.controller;

import com.gmy.blog.dto.CommentBackDTO;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmydl
 * @title: CommentController
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/19 23:56
 */
@Api(tags = "后台评论模块")
@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询后台评论
     *
     * @param condition 条件
     * @return {@link Result<CommentBackDTO>} 后台评论
     */
    @ApiOperation(value = "查询后台评论")
    @GetMapping("/list")
    public Result<PageResult<CommentBackDTO>> listCommentBackDTO(ConditionVO condition) {
        PageResult<CommentBackDTO> res = commentService.listCommentBackDTO(condition);
        return Result.ok(res);
    }


}
