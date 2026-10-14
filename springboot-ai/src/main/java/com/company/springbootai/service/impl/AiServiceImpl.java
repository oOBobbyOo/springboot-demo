package com.company.springbootai.service.impl;

import com.company.springbootai.dto.AiMessage;
import com.company.springbootai.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

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
}
