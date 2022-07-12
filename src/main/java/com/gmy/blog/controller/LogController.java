package com.gmy.blog.controller;

import com.gmy.blog.dto.OperationLogDTO;
import com.gmy.blog.service.OperationLogService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmydl
 * @title: LogController
 * @projectName blog-api
 * @description: 日志接口
 * @date 2022/7/5 12:39
 */
@Api(tags = "日志模块")
@RestController
@RequestMapping("/admin/log")
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查看所有操作日志
     *
     * @param conditionVO 条件
     * @return {@link Result<OperationLogDTO>} 日志列表
     */
    @ApiOperation(value = "查看所有操作日志")
    @GetMapping("/operationLogs")
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return Result.ok(operationLogService.listOperationLogs(conditionVO));
    }

    /**
     * 根据日志 ID 查询 日志详细信息
     * @param logId 日志 ID
     * @return 日志详细信息
     */
    @ApiOperation(value = "根据日志ID查看日志详情")
    @RequestMapping("/logsDetail")
    public Result<OperationLogDTO> operationLogsDetailById(Integer logId){
        OperationLogDTO res = operationLogService.operationLogsDetailById(logId);
        return Result.ok(res);
    }
}
