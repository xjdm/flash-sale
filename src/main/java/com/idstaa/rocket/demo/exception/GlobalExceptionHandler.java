package com.idstaa.rocket.demo.exception;

/**
 * @author chenjie
 * @date 2021/4/13 23:15
 */

import com.idstaa.rocket.demo.result.AppResult;
import com.idstaa.rocket.demo.result.ReturnWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenjie
 * @date 2021/4/13 23:14
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理请求方式(get,post,delete,put等)错误的异常
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public AppResult handlerExp(HttpRequestMethodNotSupportedException e){
        return new ReturnWrapper<>().fail(e.getMessage());
    }

    /**
     * 处理请求URL缺少必要参数的异常
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public AppResult handlerExp(MissingServletRequestParameterException e){
        return new ReturnWrapper<>().fail(e.getMessage());
    }

    /**
     * 处理对象传参非空校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AppResult handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ReturnWrapper<>().fail(e.getMessage());
    }

    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(IdstaaGlobalException.class)
    public AppResult bizException(IdstaaGlobalException e) {
        log.error("### 业务异常: {}", e.getMessage());
        e.printStackTrace();
        return new ReturnWrapper<>().fail(e.getMessage());
    }

    /**
     * 处理Exception
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public AppResult unknownException(Exception ex) {
        ex.printStackTrace();
        Throwable cause = ex.getCause();
        String msg;
        if (cause != null) {
            msg = cause.getMessage();
        } else {
            msg = ex.getMessage();
        }
        log.error("### 异常捕获: {}", msg);
        return new ReturnWrapper<>().fail(msg);
    }

}
