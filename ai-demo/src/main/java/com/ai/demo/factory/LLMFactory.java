package com.ai.demo.factory;

import com.ai.demo.factory.service.LLMService;
import com.ai.demo.factory.service.OPENAILLMService;
import com.ai.demo.factory.service.QWenLLMService;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class LLMFactory {

    public enum modelType{
        OPENAI,QWEN
    }

    public static LLMService createLLMService(modelType type){

        return switch(type){
         case OPENAI -> new OPENAILLMService();
         case QWEN -> new QWenLLMService();
        };
    }
}
