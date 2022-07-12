package com.gmy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.OperationLogDao;
import com.gmy.blog.dto.OperationLogDTO;
import com.gmy.blog.entity.OperationLogEntity;
import com.gmy.blog.service.OperationLogService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gmydl
 * @title: OperationLogServiceImpl
 * @projectName blog-api
 * @description: 操作日志服务
 * @date 2022/7/5 12:36
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLogEntity> implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO) {

        Page<OperationLogEntity> page = new Page(PageUtils.getLimitCurrent(), PageUtils.getSize());

        Page<OperationLogEntity> operationLogPage = operationLogDao.selectPage(page, new LambdaQueryWrapper<OperationLogEntity>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLogEntity::getOptModule, conditionVO.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLogEntity::getOptDesc, conditionVO.getKeywords())
                .orderByDesc(OperationLogEntity::getId));
        List<OperationLogDTO> operationLogDTOList = BeanCopyUtils.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageResult<>(operationLogDTOList, (int) operationLogPage.getTotal());
    }

    @Override
    public OperationLogDTO operationLogsDetailById(Integer logId) {
        OperationLogEntity operationLogEntity = operationLogDao.selectById(logId);
        OperationLogDTO res = BeanCopyUtils.copyObject(operationLogEntity, OperationLogDTO.class);
        return res;
    }
}
