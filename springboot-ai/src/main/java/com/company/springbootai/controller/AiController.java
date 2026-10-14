package com.company.springbootai.controller;

import com.company.springbootai.dto.AiMessage;
import com.company.springbootai.service.AiService;
import lombok.extern.slf4j.Slf4j;
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

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody AiMessage message) {
        String content = aiService.getAiResponse(message);

        log.info("aiService response: {}", content);

        return content;
    }


}
