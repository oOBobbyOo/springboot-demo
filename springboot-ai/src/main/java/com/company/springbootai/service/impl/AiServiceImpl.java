package com.company.springbootai.service.impl;

import com.company.springbootai.dto.ChatRequest;
import com.company.springbootai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Henry
 */
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

  private final ChatClient chatClient;

  /**
   * 同步调用大模型，等待返回完整回复
   *
   * @param request 会话ID与用户输入的消息
   * @return 模型返回的文本内容
   */
  @Override
  public String getAiResponse(ChatRequest request) {
    return prompt(request)
            .call()
            .content();
  }

  /**
   * 流式调用大模型，逐段返回输出
   *
   * @param request 会话ID与用户输入的消息
   * @return 模型逐段返回的文本内容
   */
  @Override
  public Flux<String> getAiStreamResponse(ChatRequest request) {
    return prompt(request)
            .stream()
            .content();
  }

  /**
   * 组装请求：传入消息，并通过会话ID关联多轮记忆
   *
   * @param request 会话ID与用户输入的消息
   * @return 已配置完成、可直接执行的请求对象
   */
  private ChatClient.ChatClientRequestSpec prompt(ChatRequest request) {
    return chatClient.prompt()
            .user(request.message())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.conversationId()));
  }
}
