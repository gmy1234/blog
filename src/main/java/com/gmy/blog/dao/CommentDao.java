package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.CommentBackDTO;
import com.gmy.blog.dto.CommentDTO;
import com.gmy.blog.entity.CommentEntity;
import com.gmy.blog.vo.CommentVO;
import com.gmy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author gmydl
 * @title: CommentEntity
 * @projectName blog-api
 * @description: 评论实体
 * @date 2022/6/19 23:51
 */
@Repository
public interface CommentDao extends BaseMapper<CommentEntity> {


    /**
     * 统计后台评论数量
     *
     * @param condition 条件
     * @return 评论数量
     */
    Integer countCommentDTO(@Param("condition") ConditionVO condition);

    /**
     * 分页统计后台评论数量
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 评论集合
     */
    List<CommentBackDTO> listCommentBackDTO(@Param("current") Long current,
                                            @Param("size") Long size,
                                            @Param("condition") ConditionVO condition);

    /**
     * 查看前台页面的评论
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDTO> getComments(@Param("current") Long current,
                                 @Param("size") Long size,
                                 @Param("commentVO") CommentVO commentVO);
}
