-- auto-generated definition
create table chat_msg
(
    id          bigint auto_increment comment 'id'
        primary key,
    uid         int                                null,
    message     longtext                           null comment '消息',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment 'ai聊天' collate = utf8mb4_unicode_ci;

eFs5HtfBkZQcNQ9hlkvPnjhR3GQC9G6v