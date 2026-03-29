package com.ai.demo.aiService;

import com.ai.demo.domain.vo.Person;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "qwenChatModel", chatMemoryProvider = "chatMemoryMysqlProvider")
public interface SeprateAssistantMysql {

    //系统提示词你是我的好朋友,请用河南话回答我,今天是{{current_date}}
    @SystemMessage(fromResource = "my_sysMessage.txt")
    String chat(@MemoryId int memoryId,@UserMessage String userMessage);

    //用户提示词
    @UserMessage("你是我的好朋友,请用河南话回答我,{{userMessage}}")
    String chat1(@V("userMessage") String userMessage);

    //用户提示词
    @SystemMessage(fromResource = "my_sysMessage2.txt")
    String chat2(@MemoryId int memoryId,@UserMessage String userMessage,@V("name") String name,@V("age") Integer age);
}
