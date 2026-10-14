package com.company.springbootai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Henry
 */
@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        String content = chatClient.prompt()
                .user(request.message())
                .call()
                .content();

        log.info("ai response: {}", content);

        return content;
    }

    public record ChatRequest(String message) {
    }
}
