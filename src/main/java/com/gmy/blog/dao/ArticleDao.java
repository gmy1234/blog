package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.gmy.blog.dto.ArticleBackDTO;
import com.gmy.blog.entity.ArticleEntity;
import com.gmy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmydl
 * @title: ArticleDao
 * @projectName blog-api
 * @description: TODO
 * @date 2022/5/24 21:42
 */
@Repository
public interface ArticleDao extends BaseMapper<ArticleEntity> {

    Integer getCountArticle(@Param("condition") ConditionVO condition);

    /**
     *
     * @param limitCurrent 起始位置
     * @param size 页码大小
     * @param condition 搜索条件
     * @return 文章列表
     */
    List<ArticleBackDTO> getListArticle(@Param("current") Long limitCurrent,
                                        @Param("size") Long size, @Param("condition") ConditionVO condition);
}
