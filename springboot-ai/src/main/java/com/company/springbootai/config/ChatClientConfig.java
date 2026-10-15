package com.company.springbootai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Henry
 */
@Configuration
public class ChatClientConfig {

    /**
     * 基于滑动窗口的对话记忆，仅保留最近的消息；存储在内存中，应用重启后会丢失。
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .build();
    }

    /**
     * 默认的 ChatClient，设置默认系统提示词，并通过 MessageChatMemoryAdvisor 自动将历史消息带入每次请求，同时保存本轮回复。
     * 标记为 @Primary，未通过 @Qualifier 指定时注入的都是该实例。
     */
    @Primary
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                // 默认系统提示词
                .defaultSystem("你是一个乐于助人的 AI 助手，请使用简体中文，准确、简洁地回答用户的问题。")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    /**
     * 翻译用途的 ChatClient，使用独立的系统提示词且不携带会话记忆，需通过 @Qualifier("translateChatClient") 注入。
     */
    @Bean
    public ChatClient translateChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem("你是一名专业翻译，请将用户输入准确地翻译为简体中文，只输出译文，不做解释。")
                .build();
    }

    /**
     * 代码专家用途的 ChatClient，使用独立的系统提示词，并通过 MessageChatMemoryAdvisor 保留多轮会话记忆，需通过 @Qualifier("codeExpertChatClient") 注入。
     */
    @Bean
    public ChatClient codeExpertChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultSystem("你是一名资深软件工程师和代码专家，精通 Java、Spring 等主流技术栈。请使用简体中文回答，给出准确、可运行的代码示例，并简要说明关键思路与潜在风险。")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}
