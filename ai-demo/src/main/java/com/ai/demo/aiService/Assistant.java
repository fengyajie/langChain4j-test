package com.ai.demo.aiService;

import com.ai.demo.domain.vo.Person;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "qwenChatModel",chatMemory = "chatMemory")
public interface Assistant {

    String chat(String message);

    @UserMessage("extract infomation about a person from {{it}}")
    Person extractPerson(String text);
}