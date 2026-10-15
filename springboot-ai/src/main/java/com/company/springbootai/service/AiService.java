package com.company.springbootai.service;

import com.company.springbootai.dto.ChatRequest;
import reactor.core.publisher.Flux;

/**
 * @author Henry
 */
public interface AiService {
  /**
   * 调用大模型获取回复内容
   *
   * @param request 会话ID与用户输入的消息
   * @return 模型返回的文本内容
   */
  String getAiResponse(ChatRequest request);

  /**
   * 调用大模型流式获取回复内容
   *
   * @param request 会话ID与用户输入的消息
   * @return 模型逐段返回的文本内容
   */
  Flux<String> getAiStreamResponse(ChatRequest request);

  /**
   * 调用代码专家大模型流式获取回复内容
   *
   * @param request 会话ID与用户输入的消息
   * @return 代码专家模型逐段返回的文本内容
   */
  Flux<String> getCodeExpertStreamResponse(ChatRequest request);
}
