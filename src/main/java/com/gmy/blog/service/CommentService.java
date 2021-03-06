package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.comment.CommentBackDTO;
import com.gmy.blog.dto.comment.CommentDTO;
import com.gmy.blog.dto.comment.ReplyDTO;
import com.gmy.blog.entity.CommentEntity;
import com.gmy.blog.vo.CommentVO;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.ReviewVO;

import java.util.List;

/**
 * @author gmydl
 * @title: CommentService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/19 23:54
 */
public interface CommentService extends IService<CommentEntity> {
    /**
     * 分页条件查询后台评论
     * @param condition 条件
     * @return 评论列表
     */
    PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition);

    /**
     * 审核评论
     * @param reviewVO 评论的id和状态
     */
    void updateCommentsReview(ReviewVO reviewVO);

    /**
     * 获取所有评论
     * @param commentVO  参数
     * @return 评论
     */
    PageResult<CommentDTO> getComments(CommentVO commentVO);

    /**
     * 保存评论
     * @param commentVO 评论信息
     */
    void saveComment(CommentVO commentVO);

    /**
     * 点赞评论
     * @param commentId 评论 ID
     */
    void likeComment(Integer commentId);

    /**
     * 根据评论 ID 查询评论下的回复
     * @param commentId 评论 ID
     * @return 回复
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);
}
