package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.TagDao;
import com.gmy.blog.dto.TagBackDTO;
import com.gmy.blog.dto.TagDTO;
import com.gmy.blog.entity.TagEntity;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.TagService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import com.gmy.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author gmydl
 * @title: TagServiceImpl
 * @projectName blog
 * @description: TODO
 * @date 2022/5/27 12:19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public PageResult<TagBackDTO> getAllTagBackDTO(ConditionVO condition) {
        // 查询标签数量
        Long count = tagDao.selectCount(new LambdaQueryWrapper<TagEntity>()
                .like(StringUtils.isNotBlank(condition.getKeywords()
                ), TagEntity::getTagName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackDTO> tagList = tagDao.getAllTagBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<TagBackDTO>(tagList, Math.toIntExact(count));
    }

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVO condition) {
        // 搜索标签
        List<TagEntity> tagList = tagDao.selectList(new LambdaQueryWrapper<TagEntity>()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        TagEntity::getTagName, condition.getKeywords())
                .orderByDesc(TagEntity::getId));
        return BeanCopyUtils.copyList(tagList, TagDTO.class);
    }

    @Override
    public void saveOrUpdateTag(TagVo tagVO) {
        // 判断是否已经存着
        TagEntity existTag = tagDao.selectOne(new LambdaQueryWrapper<TagEntity>()
                .select(TagEntity::getId)
                .eq(TagEntity::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && existTag.getId().equals(tagVO.getId())){
            throw new BizException("标签已经存在");
        }
        // 不存在，插入或更新数据
        TagEntity tagEntity = BeanCopyUtils.copyObject(tagVO, TagEntity.class);
        this.saveOrUpdate(tagEntity);
    }

    @Override
    public void deletedTag(Integer id) {
        tagDao.deleteById(id);
    }
}
