package com.company.mybatisplus.exception;

import com.company.mybatisplus.common.Result;
import com.company.mybatisplus.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Henry
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 处理业务异常
   *
   * @param e 异常
   * @return 统一响应结果
   */
  @ExceptionHandler(BusinessException.class)
  public Result<Void> handleBusinessException(BusinessException e) {
    log.warn("业务异常：{}", e.getMessage());
    return Result.error(e.getCode(), e.getMessage());
  }

  /**
   * 处理参数校验异常
   *
   * @param e 异常
   * @return 统一响应结果
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<Void> handleValidException(MethodArgumentNotValidException e) {
    String message =
        e.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .findFirst()
            .orElse(ResultCode.PARAM_VALID_FAIL.getMessage());

    return Result.error(ResultCode.PARAM_VALID_FAIL.getCode(), message);
  }

  /**
   * 其他未知异常
   *
   * @param e 异常
   * @return 统一响应结果
   */
  @ExceptionHandler(Exception.class)
  public Result<Void> handleException(Exception e) {
    e.printStackTrace();
    log.error("系统异常：", e);
    return Result.error(ResultCode.INTERNAL_ERROR.getMessage());
  }
}
