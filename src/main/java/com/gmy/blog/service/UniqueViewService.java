package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.UniqueViewDTO;
import com.gmy.blog.entity.UniqueViewEntity;

import java.util.List;


/**
 * 用户量统计
 *
 * @author xiaojie
 * @date 2021/07/29
 */
public interface UniqueViewService extends IService<UniqueViewEntity> {


    /**
     * 获取7天用户量统计
     *
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews();
}
