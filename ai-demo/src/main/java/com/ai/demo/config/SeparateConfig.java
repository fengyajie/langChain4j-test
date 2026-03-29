package com.ai.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ai.demo.chatmemory.PersistentChatMemoryStore;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

@Configuration
public class SeparateConfig {

    private final PersistentChatMemoryStore persistentChatMemoryStore;
    
    // 构造器注入 PersistentChatMemoryStore
    public SeparateConfig(PersistentChatMemoryStore persistentChatMemoryStore) {
        this.persistentChatMemoryStore = persistentChatMemoryStore;
    }

    @Bean
    ChatMemoryProvider chatMemoryProvider(){
        //
        return memoryId -> MessageWindowChatMemory.builder().id(memoryId).chatMemoryStore(new InMemoryChatMemoryStore()).maxMessages(10).build();
    }

    @Bean
    ChatMemoryProvider chatMemoryMysqlProvider(){
        //
        return memoryId -> MessageWindowChatMemory.builder().id(memoryId).chatMemoryStore(persistentChatMemoryStore).maxMessages(10).build();
    }
}
