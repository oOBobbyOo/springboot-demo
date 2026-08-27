package com.company.security.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 全局统一响应体
 * @param <T> 响应数据类型
 *
 * @author Henry
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   /**
    * 业务状态码 (参考 ResultCode 枚举)
    */
   private Integer code;

   /**
    * 提示信息
    */
   private String message;

   /**
    * 响应数据 (泛型)
    */
   private T data;

   /**
    * 响应时间戳
    */
   private Long timestamp;

   /**
    * 私有构造器，强制使用静态工厂方法创建实例
    */
   private Result(Integer code, String message, T data) {
      this.code = code;
      this.message = message;
      this.data = data;
      this.timestamp = System.currentTimeMillis();
   }

   // ================= 静态工厂方法 =================

   /**
    * 成功响应 (无数据)
    */
   public static <T> Result<T> success() {
      return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
   }

   /**
    * 成功响应 (带数据)
    */
   public static <T> Result<T> success(T data) {
      return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
   }

   /**
    * 失败响应 (使用枚举)
    */
   public static <T> Result<T> fail(ResultCode resultCode) {
      return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
   }

   /**
    * 失败响应 (自定义码和消息)
    */
   public static <T> Result<T> fail(Integer code, String message) {
      return new Result<>(code, message, null);
   }

   /**
    * 失败响应 (带数据，常用于返回具体的校验错误详情)
    */
   public static <T> Result<T> fail(Integer code, String message, T data) {
      return new Result<>(code, message, data);
   }

   /**
    * 链式调用：允许在构建后动态修改某些字段 (可选)
    */
   public Result<T> message(String message) {
      this.message = message;
      return this;
   }
}