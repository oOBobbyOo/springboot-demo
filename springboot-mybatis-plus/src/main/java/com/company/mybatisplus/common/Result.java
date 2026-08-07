package com.company.mybatisplus.common;

import lombok.Data;

/**
 * 统一响应结果类
 *
 * <p>用于封装后端接口返回给前端的统一数据格式
 *
 * @param <T> 响应数据类型
 * @author Henry
 * @since 2026-08-07
 */
@Data
public class Result<T> {

  /** 成功状态码 */
  public static final int SUCCESS_CODE = 200;

  /** 失败状态码 */
  public static final int ERROR_CODE = 500;

  /**
   * 响应状态码
   *
   * <p>200 表示成功，500 表示失败
   */
  private Integer code;

  /** 响应提示信息 */
  private String message;

  /** 响应数据 */
  private T data;

  /** 空参构造方法 */
  public Result() {}

  /**
   * 全参构造方法
   *
   * @param code 状态码
   * @param message 提示信息
   * @param data 响应数据
   */
  public Result(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  /**
   * 返回成功结果，不携带数据
   *
   * @param <T> 响应数据类型
   * @return 成功结果
   */
  public static <T> Result<T> success() {
    return new Result<>(SUCCESS_CODE, "操作成功", null);
  }

  /**
   * 返回成功结果，并携带数据
   *
   * @param data 响应数据
   * @param <T> 响应数据类型
   * @return 成功结果
   */
  public static <T> Result<T> success(T data) {
    return new Result<>(SUCCESS_CODE, "操作成功", data);
  }

  /**
   * 返回成功结果，自定义提示信息并携带数据
   *
   * @param message 提示信息
   * @param data 响应数据
   * @param <T> 响应数据类型
   * @return 成功结果
   */
  public static <T> Result<T> success(String message, T data) {
    return new Result<>(SUCCESS_CODE, message, data);
  }

  /**
   * 返回成功结果，自定义提示信息，不携带数据
   *
   * @param message 成功提示信息
   * @param <T>     响应数据类型
   * @return 成功结果
   */
  public static <T> Result<T> success(String message) {
    return new Result<>(SUCCESS_CODE, message, null);
  }

  /**
   * 返回失败结果，默认错误提示信息
   *
   * @param <T> 响应数据类型
   * @return 失败结果
   */
  public static <T> Result<T> error() {
    return new Result<>(ERROR_CODE, "操作失败", null);
  }

  /**
   * 返回失败结果，自定义错误提示信息
   *
   * @param message 错误提示信息
   * @param <T> 响应数据类型
   * @return 失败结果
   */
  public static <T> Result<T> error(String message) {
    return new Result<>(ERROR_CODE, message, null);
  }

  /**
   * 返回失败结果，自定义状态码和错误提示信息
   *
   * @param code 状态码
   * @param message 错误提示信息
   * @param <T> 响应数据类型
   * @return 失败结果
   */
  public static <T> Result<T> error(Integer code, String message) {
    return new Result<>(code, message, null);
  }
}
