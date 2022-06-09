package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dao.BackgroundDao;
import com.gmy.blog.entity.BackgroundEntity;
import com.gmy.blog.vo.BackgroundVO;

import java.util.List;

/**
 * @author gmydl
 * @title: BackgroundService
 * @projectName blog-api
 * @description: TODO
 * @date 2022/6/9 20:46
 */
public interface BackgroundService extends IService<BackgroundEntity> {
    /**
     * 获取背景列表
     * @return 背景列表
     */
    List<BackgroundVO> listBackground();

    /**
     * 保存或更新页面信息
     * @param backgroundVO 信息
     */
    void saveOrUpdateBackground(BackgroundVO backgroundVO);

    /**
     * 删除背景
     * @param backgroundId 背景ID
     */
    void deleteBackground(Integer backgroundId);
}
