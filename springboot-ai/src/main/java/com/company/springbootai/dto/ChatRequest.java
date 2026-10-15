package com.company.springbootai.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Henry
 */
public record ChatRequest(
        @NotBlank(message = "会话ID不能为空") String conversationId,
        @NotBlank(message = "消息内容不能为空") String message) {
}
