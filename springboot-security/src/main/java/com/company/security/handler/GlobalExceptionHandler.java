package com.company.security.handler;

import com.company.security.common.Result;
import com.company.security.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Henry
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

   /**
    * 处理参数校验异常
    */
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Result<Void> handleValidationExceptions(MethodArgumentNotValidException ex) {
      String message = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
      return Result.fail(400, message);
   }

   /**
    * 处理自定义业务异常
    */
   @ExceptionHandler(BusinessException.class)
   public Result<Void> handleBusinessException(BusinessException ex) {
      log.warn("业务异常：{}", ex.getMessage());
      return Result.fail(ex.getCode(), ex.getMessage());
   }

   /**
    * 兜底异常
    */
   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public Result<Void> handleException(Exception ex) {
      log.error("系统未知异常", ex);
      return Result.fail(500, "系统繁忙，请稍后再试");
   }
}
