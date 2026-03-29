package com.ai.demo;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ai.demo.aiService.McpAssistant;

import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.service.AiServices;

@SpringBootTest
public class McpTest {

    @Autowired
    private QwenStreamingChatModel qwenStreamingChatModel;

    @Test
    public void mcpTest(){

        //1，构建mcp协议
        StdioMcpTransport stdioMcpTransport = new StdioMcpTransport.Builder().command(List.of("npx","-y","@baidumap/mcp-server-baidu-map"))
        .environment(Map.of("BAIDU_MAP_API_KEY", "eFs5HtfBkZQcNQ9hlkvPnjhR3GQC9G6v")).build();
        //2创建mcp client

        DefaultMcpClient defaultMcpClient = new DefaultMcpClient.Builder().transport(stdioMcpTransport).build();
     

        //3创建工具集
        McpToolProvider mcpToolProvider = McpToolProvider.builder().mcpClients(defaultMcpClient).build();

        //4，实例化aiService
        McpAssistant mcpAssistant = AiServices.builder(McpAssistant.class).streamingChatModel(qwenStreamingChatModel).toolProvider(mcpToolProvider).build();

        //调用大模型会话
        StringBuilder sb = new StringBuilder();
        mcpAssistant.chat("上海这几天天气怎么样").doOnNext(sb::append).blockLast();

        System.out.println(sb.toString());
    }
}
