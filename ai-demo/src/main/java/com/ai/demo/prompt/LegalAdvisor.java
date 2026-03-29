package com.ai.demo.prompt;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "qwenChatModel",chatMemory = "chatMemory")
public interface LegalAdvisor {

    @SystemMessage("你是一位专业的中国法律专家，只回答与中国法律相关的问题，输出限制:其他领域的问题禁止回答，直接返回：抱歉，我只回答法律相关问题")
    String answerLegalQuestion(LegalPrompt legalPrompt);

}
