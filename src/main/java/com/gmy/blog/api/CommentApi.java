package com.gmy.blog.api;

import com.gmy.blog.dto.CommentDTO;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.vo.CommentVO;
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

}
