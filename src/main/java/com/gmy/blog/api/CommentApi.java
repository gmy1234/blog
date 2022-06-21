package com.gmy.blog.api;

import com.gmy.blog.dto.comment.CommentDTO;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.vo.CommentVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author gmydl
 * @title: CommentController
 * @projectName blog-api
 * @description: 评论接口
 * @date 2022/6/19 23:55
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentApi {

    @Autowired
    private CommentService commentService;

    /**
     * 查询评论
     *
     * @param commentVO 评论信息
     * @return {@link Result<CommentDTO>}
     */
    @ApiOperation(value = "查询评论")
    @GetMapping("/getComments")
    public Result<PageResult<CommentDTO>> getComments(CommentVO commentVO) {
        PageResult<CommentDTO> result = commentService.getComments(commentVO);
        return Result.ok(result);
    }

    /**
     * 评论
     *
     * @param commentVO 评论信息
     * @return {@link Result<CommentDTO>}
     */
    @ApiOperation(value = "评论")
    @PostMapping("")
    public Result<?> comment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.ok();
    }

    /**
     * 点赞评论
     *
     * @param commentId 评论信息
     * @return {@link Result<CommentDTO>}
     */
    @ApiOperation(value = "点赞评论")
    @PostMapping("/{commentId}/like")
    public Result<?> likeComment(@PathVariable("commentId") Integer commentId) {
        commentService.likeComment(commentId);
        return Result.ok();
    }
}
