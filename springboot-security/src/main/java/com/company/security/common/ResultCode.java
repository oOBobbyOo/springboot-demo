package com.company.security.common;

import lombok.Getter;

/**
 * @author Henry
 */
@Getter
public enum ResultCode {
  // --- 成功 ---
  SUCCESS(200, "操作成功"),

  // --- 客户端错误 ---
  BAD_REQUEST(400, "请求参数错误"),
  UNAUTHORIZED(401, "未登录或登录已过期"),
  FORBIDDEN(403, "权限不足，拒绝访问"),
  NOT_FOUND(404, "请求的资源不存在"),
  TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后再试"),

  // --- 服务端错误  ---
  INTERNAL_SERVER_ERROR(500, "服务器内部异常，请稍后重试"),
  SERVICE_UNAVAILABLE(503, "服务暂时不可用");

  private final Integer code;
  private final String message;

  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
