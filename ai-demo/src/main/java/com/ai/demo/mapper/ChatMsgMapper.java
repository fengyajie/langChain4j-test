package com.ai.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ai.demo.domain.ChatMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface ChatMsgMapper extends BaseMapper<ChatMsg>{

    
}
