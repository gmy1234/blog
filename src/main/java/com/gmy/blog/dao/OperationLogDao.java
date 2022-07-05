package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.entity.OperationLogEntity;
import org.springframework.stereotype.Repository;


/**
 * 操作日志
 *
 * @author gmy
 * @date 2021/07/5
 */
@Repository
public interface OperationLogDao extends BaseMapper<OperationLogEntity> {
}
