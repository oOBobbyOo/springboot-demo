package com.company.mybatisplus.common;

import lombok.Getter;

/**
 * 统一响应状态码枚举
 *
 * <p>用于统一封装接口返回的业务状态码和提示信息。
 *
 * @author Henry
 */
@Getter
public enum ResultCode {

  /** 成功 */
  SUCCESS(200, "操作成功"),

  /** 客户端错误 4xx */
  BAD_REQUEST(400, "请求参数错误"),
  UNAUTHORIZED(401, "未授权，请先登录"),
  FORBIDDEN(403, "无权限访问"),
  NOT_FOUND(404, "资源不存在"),
  METHOD_NOT_ALLOWED(405, "请求方法不允许"),
  CONFLICT(409, "数据冲突"),

  /** 服务端错误 5xx */
  INTERNAL_ERROR(500, "系统异常"),
  SERVICE_UNAVAILABLE(503, "服务暂不可用"),

  /** 业务错误码（自定义区间） */
  USER_NOT_EXIST(1001, "用户不存在"),
  PASSWORD_ERROR(1002, "密码错误"),
  USERNAME_EXIST(1003, "用户名已存在"),
  PARAM_VALID_FAIL(1004, "参数校验失败"),
  TOKEN_INVALID(1005, "Token无效或已过期"),
  FILE_UPLOAD_FAIL(1006, "文件上传失败"),
  DATA_NOT_FOUND(1007, "数据查询失败"),

  /** 角色相关业务错误码 */
  ROLE_NOT_EXIST(2001, "角色不存在"),
  ROLE_CODE_EXIST(2002, "角色编码已存在"),
  ROLE_NAME_EXIST(2003, "角色名称已存在"),
  ROLE_HAS_USERS(2004, "该角色下存在用户，不能删除");

  /** 业务状态码 */
  private final int code;

  /** 提示信息 */
  private final String message;

  /**
   * 构造方法
   *
   * @param code 业务状态码
   * @param message 提示信息
   */
  ResultCode(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
