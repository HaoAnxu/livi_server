package com.anxu.smarthomeunity.exception;

import com.anxu.smarthomeunity.pojo.Result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理类
 *
 * @Author: haoanxu
 * @Date: 2025/11/18 16:50
 */


// 全局异常处理，作用于所有 @Controller/@RestController
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 捕获 Assert 断言失败的异常（IllegalArgumentException/IllegalStateException）
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public Result handleValidateException(RuntimeException e) {
        // 直接把 Assert 里的提示信息（如“用户名不能为空”）返回给前端
        return Result.error(e.getMessage());
    }

    // 捕获其他未知异常（兜底）
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 生产环境可以日志打印详细异常，前端只显示友好提示
        log.error("系统异常：", e);
        return Result.error("系统繁忙，请稍后重试");
    }
}
