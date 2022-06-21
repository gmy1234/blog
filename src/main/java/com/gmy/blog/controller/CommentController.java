package com.gmy.blog.controller;

import com.gmy.blog.dto.comment.CommentBackDTO;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.ReviewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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


    /**
     * 删除评论
     *
     * @param commentIdList 评论id列表
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除评论")
    @PostMapping("/delete")
    public Result<?> deleteComments(@RequestBody Map<String, List<Integer>> commentIdList) {
        commentService.removeByIds(commentIdList.get("data"));
        return Result.ok();
    }


    /**
     * 审核评论
     *
     * @param reviewVO 审核信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "审核评论")
    @PostMapping("/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.ok();
    }


}
