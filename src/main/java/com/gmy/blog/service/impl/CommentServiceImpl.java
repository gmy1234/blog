package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.ArticleDao;
import com.gmy.blog.dao.CommentDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dto.comment.CommentBackDTO;
import com.gmy.blog.dto.comment.CommentDTO;
import com.gmy.blog.dto.comment.ReplyCountDTO;
import com.gmy.blog.dto.comment.ReplyDTO;
import com.gmy.blog.entity.CommentEntity;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.CommentService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.util.HTMLUtils;
import com.gmy.blog.util.UserUtils;
import com.gmy.blog.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.FALSE;
import static com.gmy.blog.constant.CommonConst.TRUE;
import static com.gmy.blog.constant.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.gmy.blog.constant.RedisPrefixConst.COMMENT_USER_LIKE;

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
        // ?????????????????????
        Integer count = commentDao.countCommentDTO(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        // ????????????????????????
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(commentBackDTOList, count);
    }

    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // ????????????????????????
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
        // ????????????????????????
        Long commentCount = commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(Objects.nonNull(commentVO.getTopicId()), CommentEntity::getTopicId, commentVO.getTopicId())
                .eq(CommentEntity::getType, commentVO.getType())
                .isNull(CommentEntity::getParentId)
                .eq(CommentEntity::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // ???????????????????????????
        List<CommentDTO> commentDTOList = commentDao.getComments(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // ?????? redis ????????????????????????
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);

        // TODO:????????????????????????????????????
        // 1?????????????????? ID
        List<Integer> commentParentId = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        // 2??????????????????ID????????????????????????
        List<ReplyDTO> replyDTOList =  commentDao.listReplies(commentParentId);
        // 3????????????????????????
        replyDTOList.forEach(item ->{
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
        });
        // 4??????????????? id ????????????????????? parentID -> ?????????????????????
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 5??????????????? id ???????????????
        List<ReplyCountDTO> replyCount = commentDao.listReplyCountByCommentId(commentParentId);
        Map<Integer, Integer> replyCountMap = replyCount.stream().
                collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));

        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            // ??????????????????????????????
            item.setReplyDTOList(replyMap.get(item.getId()));
            // ????????????????????????????????????
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, Math.toIntExact(commentCount));
    }


    /**
     * ??????????????????
     */
    private void notice(CommentEntity comment){
        // TODO ????????????

    }


    @Override
    public void saveComment(CommentVO commentVO) {
        // ????????????????????????
        WebsiteConfigVO websiteConfig = blogInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();

        // ????????????
        commentVO.setCommentContent(HTMLUtils.filter(commentVO.getCommentContent()));
        CommentEntity comment = CommentEntity.builder()
                .userId(UserUtils.getLoginUser().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isReview == TRUE ? FALSE : TRUE)
                .build();
        commentDao.insert(comment);

        // ??????????????????????????????,????????????
        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> notice(comment));
        }
    }

    @Override
    public void likeComment(Integer commentId) {
        // ????????????key
        String commentLikeKey = COMMENT_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();

        // ????????????????????? ????????????
        if (redisService.sIsMember(commentLikeKey, commentId)){
            // ????????????????????????
            redisService.sRemove(commentLikeKey, commentId);
            // ??????-1
            redisService.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }else {
            // ????????????????????????,??????
            redisService.sAdd(commentLikeKey, commentId);
            redisService.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        // ????????????????????????????????????
        List<ReplyDTO> replyDTOList = commentDao.listRepliesByCommentId(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentId);
        // ??????redis?????????????????????
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        // ??????????????????
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }
}
