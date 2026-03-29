package com.ai.demo.factory.service;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class OPENAILLMService implements LLMService{

    public final OpenAiChatModel openAiChatModel;

    public OPENAILLMService(){
        this.openAiChatModel = OpenAiChatModel.builder().apiKey("demo").baseUrl("http://langchain4j.dev/demo/openai/v1").modelName("gpt-4o-mini").build();
    }
    

    @Override
    public String chat(String message) {
        return openAiChatModel.chat(message);
    }

}
