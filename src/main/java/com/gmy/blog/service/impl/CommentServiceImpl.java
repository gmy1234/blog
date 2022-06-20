package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.CommentDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dto.CommentBackDTO;
import com.gmy.blog.dto.CommentDTO;
import com.gmy.blog.entity.CommentEntity;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.TRUE;

/**
 * @author gmydl
 * @title: CommentServiceImpl
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/19 23:54
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserInfoDao userInfoDao;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BlogInfoService blogInfoService;

    @Override
    public PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition) {
        // 统计后台评论量
        Integer count = commentDao.countCommentDTO(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(commentBackDTOList, count);
    }

    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // 修改评论审核状态
        List<CommentEntity> commentList = reviewVO.getIdList().stream()
                .map(item -> CommentEntity.builder()
                        .id(item)
                        .isReview(reviewVO.getIsReview())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }

    @Override
    public PageResult<CommentDTO> getComments(CommentVO commentVO) {
        // 查询根评论的数量
        Long commentCount = commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(Objects.nonNull(commentVO.getTopicId()), CommentEntity::getTopicId, commentVO.getTopicId())
                .eq(CommentEntity::getType, commentVO.getType())
                .isNull(CommentEntity::getParentId)
                .eq(CommentEntity::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询父评论数据
        List<CommentDTO> commentDTOList = commentDao.getComments(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentVO);

        // TODO:查询每个父评论的回复评论



        return new PageResult<>(commentDTOList, Math.toIntExact(commentCount));
    }
}
