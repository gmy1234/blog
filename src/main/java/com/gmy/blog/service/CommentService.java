package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.CommentBackDTO;
import com.gmy.blog.entity.CommentEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;

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
}
