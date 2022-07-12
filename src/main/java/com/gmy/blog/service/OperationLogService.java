package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.OperationLogDTO;
import com.gmy.blog.entity.OperationLogEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;

/**
 * @author gmydl
 * @title: OperationLogService
 * @projectName blog-api
 * @description: 操作日志服务
 * @date 2022/7/5 12:30
 */
public interface OperationLogService extends IService<OperationLogEntity> {
    /**
     * 查询操作日志
     * @param conditionVO 条件
     * @return 操作日志
     */
    PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

    /**
     * 根据日志 ID 查看日志详细信息
     * @param logId 日志 ID
     * @return 详细信息
     */
    OperationLogDTO operationLogsDetailById(Integer logId);
}
