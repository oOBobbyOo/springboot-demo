package com.company.springbootai.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Henry
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    // 成功
    SUCCESS(200, "成功"),
    // 失败
    FAIL(500, "系统异常");

    /**
     * 业务状态码
     */
    private final int code;

    /**
     * 状态描述信息
     */
    private final String message;
}
