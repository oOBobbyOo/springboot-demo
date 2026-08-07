package com.company.mybatisplus.exception;

import com.company.mybatisplus.common.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Henry
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public Result<Void> handleBusinessException(BusinessException e) {
    return Result.error(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<Void> handleValidException(MethodArgumentNotValidException e) {
    String message =
        e.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .findFirst()
            .orElse("参数错误");

    return Result.error(400, message);
  }

  @ExceptionHandler(Exception.class)
  public Result<Void> handleException(Exception e) {
    e.printStackTrace();
    return Result.error("系统异常");
  }
}
