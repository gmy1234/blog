package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UniqueViewDao;
import com.gmy.blog.entity.UniqueViewEntity;

import com.gmy.blog.service.UniqueViewService;
import org.springframework.stereotype.Service;


/**
 * 访问量统计服务
 *
 * @author gmy
 * @date 2021/08/06
 */
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewDao, UniqueViewEntity> implements UniqueViewService {

}
