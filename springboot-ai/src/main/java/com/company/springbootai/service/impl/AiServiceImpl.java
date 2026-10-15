package com.company.springbootai.service.impl;

import com.company.springbootai.dto.ChatRequest;
import com.company.springbootai.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Henry
 */
@Service
public class AiServiceImpl implements AiService {

  private final ChatClient chatClient;
  private final ChatClient codeExpertChatClient;

  public AiServiceImpl(ChatClient chatClient,
                       @Qualifier("codeExpertChatClient") ChatClient codeExpertChatClient) {
    this.chatClient = chatClient;
    this.codeExpertChatClient = codeExpertChatClient;
  }

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
   * 调用代码专家大模型流式获取回复内容，并通过会话ID保留多轮记忆
   *
   * @param request 会话ID与用户输入的消息
   * @return 代码专家模型逐段返回的文本内容
   */
  @Override
  public Flux<String> getCodeExpertStreamResponse(ChatRequest request) {
    return codeExpertChatClient.prompt()
            .user(request.message())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.conversationId()))
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
