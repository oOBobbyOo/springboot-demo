package com.company.springbootai.service;

import com.company.springbootai.dto.AiMessage;
import reactor.core.publisher.Flux;

/**
 * @author Henry
 */
public interface AiService {
  /**
   * 调用大模型获取回复内容
   *
   * @param message 用户输入的消息
   * @return 模型返回的文本内容
   */
  String getAiResponse(AiMessage message);

  /**
   * 调用大模型流式获取回复内容
   *
   * @param message 用户输入的消息
   * @return 模型逐段返回的文本内容
   */
  Flux<String> getAiStreamResponse(AiMessage message);
}
