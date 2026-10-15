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

  @Override
  public String getAiResponse(ChatRequest request) {
    return chatClient.prompt()
            .user(request.message())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.conversationId()))
            .call()
            .content();
  }

  @Override
  public Flux<String> getAiStreamResponse(ChatRequest request) {
    return chatClient.prompt()
            .user(request.message())
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.conversationId()))
            .stream()
            .content();
  }
}
