package com.company.springbootai.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Henry
 */
public record AiMessage(@NotBlank(message = "消息内容不能为空") String message) {
}
