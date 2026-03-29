package com.ai.demo.product;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "qwenChatModel",chatMemoryProvider = "chatMemoryProvider",tools = "productTool")
public interface ProductAiService {

    @SystemMessage("你是一个电商平台的机器人,帮助用户查询商品信息,请记住用户询问过的商品信息，以便在后续对话中提供信息")
    String chat(@MemoryId  String memoryId, @UserMessage String message);
}
