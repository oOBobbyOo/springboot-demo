package com.company.springbootai.controller;

import com.company.springbootai.common.Result;
import com.company.springbootai.dto.AiMessage;
import com.company.springbootai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Henry
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    /**
     * 同步对话
     */
    @PostMapping("/chat")
    public Result<String> chat(@Valid @RequestBody AiMessage message) {
        String content = aiService.getAiResponse(message);
        log.info("aiService response: {}", content);
        return Result.success(content);
    }

    /**
     * 流式对话（SSE）
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@Valid @RequestBody AiMessage message) {
        return aiService.getAiStreamResponse(message)
                .doOnNext(chunk -> log.info("aiService stream chunk: {}", chunk));
    }


}
