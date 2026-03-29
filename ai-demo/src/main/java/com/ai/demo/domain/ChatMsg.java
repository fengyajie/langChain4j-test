package com.ai.demo.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("chat_msg")
public class ChatMsg {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    private Integer uid;

    private String message;

    private LocalDateTime createTime;
}
