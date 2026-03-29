package com.ai.demo.chatmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.demo.domain.ChatMsg;
import com.ai.demo.mapper.ChatMsgMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

@Component
public class PersistentChatMemoryStore implements ChatMemoryStore{

    private final ChatMsgMapper chatMsgMapper;
    
    // 使用构造器注入
    public PersistentChatMemoryStore(ChatMsgMapper chatMsgMapper) {
        this.chatMsgMapper = chatMsgMapper;
    }

    
    @Override
    public void deleteMessages(Object memoryId) {
        ChatMsg chatMsg = chatMsgMapper.selectOne(Wrappers.<ChatMsg>lambdaQuery().eq(ChatMsg::getUid, memoryId));
        if(Objects.nonNull(chatMsg)){
            chatMsgMapper.deleteById(chatMsg.getId());
        }
        
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        ChatMsg chatMsg = chatMsgMapper.selectOne(Wrappers.<ChatMsg>lambdaQuery().eq(ChatMsg::getUid, memoryId));
        if(Objects.isNull(chatMsg)){

            return new ArrayList<>();
        }
        ;
        return ChatMessageDeserializer.messagesFromJson(chatMsg.getMessage());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> chatMessages) {
       
        // 将ChatMessage列表序列化为JSON
        String messagesJson = ChatMessageSerializer.messagesToJson(chatMessages);
        ChatMsg chatMsg = chatMsgMapper.selectOne(Wrappers.<ChatMsg>lambdaQuery().eq(ChatMsg::getUid, memoryId));
        if(Objects.isNull(chatMsg)){
            ChatMsg chatMsg2 = new ChatMsg();
            chatMsg2.setUid(Integer.parseInt(memoryId+""));
            chatMsg2.setMessage(messagesJson);

            chatMsgMapper.insert(chatMsg2);
        }else{
            
            chatMsg.setUid(Integer.parseInt(memoryId+""));
            chatMsg.setMessage(messagesJson);

            chatMsgMapper.updateById(chatMsg);
        }
    }

}
