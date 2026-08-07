package com.company.mybatisplus.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Henry
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

  private final Integer code;

  public BusinessException(Integer code, String message) {
    super(message);
    this.code = code;
  }
}
