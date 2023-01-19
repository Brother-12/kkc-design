package com.kerco.kkc.common.handler;

import com.kerco.kkc.common.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉，将错误信息传入到统一结果返回，而不是返回错误页面信息
 *
 * 使用logback+log4j实现统一日志管理
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 在全局中对所有异常进行处理
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResult handleException(RuntimeException e){
        //TODO 需要做一个日志 用来存放错误信息
        return CommonResult.error(20000,e.getMessage());
    }
}
