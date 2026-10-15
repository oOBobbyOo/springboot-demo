package com.company.springbootai.service.impl;

import com.company.springbootai.dto.AiMessage;
import com.company.springbootai.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Henry
 */
@Service
public class AiServiceImpl implements AiService {

  private final ChatClient chatClient;

  public AiServiceImpl(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public String getAiResponse(AiMessage message) {
    return chatClient.prompt()
            .user(message.message())
            .call()
            .content();
  }

  @Override
  public Flux<String> getAiStreamResponse(AiMessage message) {
    return chatClient.prompt()
            .user(message.message())
            .stream()
            .content();
  }
}
