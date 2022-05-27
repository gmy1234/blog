package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dao.TagDao;
import com.gmy.blog.dto.TagBackDTO;
import com.gmy.blog.dto.TagDTO;
import com.gmy.blog.entity.CategoryEntity;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gmydl
 * @title: TagService
 * @projectName blog
 * @description: TODO
 * @date 2022/5/27 12:19
 */

public interface TagService extends IService<TagEntity> {
    /**
     * 获取后台标签
     * @param condition
     * @return
     */
    PageResult<TagBackDTO> getAllTagBackDTO(ConditionVO condition);

    /**
     * 搜索标签
     * @param condition
     * @return
     */
    List<TagDTO> listTagsBySearch(ConditionVO condition);

    void saveOrUpdateTag(TagVo tagVO);

    void deletedTag(Integer id);
}
