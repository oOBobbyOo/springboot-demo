package com.company.springbootai.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 对话请求参数
 *
 * @param conversationId 会话ID，同一会话内的消息共享多轮对话记忆，必填
 * @param message        用户输入的消息内容，必填
 * @author Henry
 */
public record ChatRequest(
        @NotBlank(message = "会话ID不能为空") String conversationId,
        @NotBlank(message = "消息内容不能为空") String message) {
}
