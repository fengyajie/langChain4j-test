package com.ai.demo.tool;


import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

//tools执行工具类
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "qwenChatModel",chatMemoryProvider = "chatMemoryProvider",tools = "calculatorTool")
public interface AssistantTool {

    String chat(String message);
}