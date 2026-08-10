package com.company.mybatisplus.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义业务异常
 *
 * @author Henry
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

  /**
   * 状态码
   */
  private final Integer code;

  /**
   * 业务异常
   *
   * @param code    状态码
   * @param message 错误信息
   */
  public BusinessException(Integer code, String message) {
    super(message);
    this.code = code;
  }
}
