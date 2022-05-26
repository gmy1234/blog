package com.gmy.blog.exception;

import com.gmy.blog.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.gmy.blog.enums.StatusCodeEnum.FAIL;
/**
 * @author gmydl
 * @title: BizException
 * @projectName blog
 * @description: 异常类
 * @date 2022/5/26 14:43
 */
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }


}
