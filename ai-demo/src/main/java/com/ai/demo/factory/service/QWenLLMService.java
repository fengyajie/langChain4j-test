package com.ai.demo.factory.service;

import dev.langchain4j.community.model.dashscope.QwenChatModel;

public class QWenLLMService implements LLMService{

    private final QwenChatModel qwenChatModel;

    public QWenLLMService(){
        this.qwenChatModel = QwenChatModel.builder().build();
    }

    @Override
    public String chat(String message) {
        return qwenChatModel.chat(message);
    
    }

}
