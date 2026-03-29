package com.ai.demo;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ai.demo.aiService.Assistant;
import com.ai.demo.aiService.SeprateAssistant;
import com.ai.demo.aiService.SeprateAssistantMysql;
import com.ai.demo.domain.vo.Person;
import com.ai.demo.product.ProductAiService;
import com.ai.demo.prompt.LegalAdvisor;
import com.ai.demo.prompt.LegalPrompt;
import com.ai.demo.tool.AssistantTool;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.internal.Json;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;

@SpringBootTest
public class DemoApplicationTest {

    @Test
    public void contextLoads() {
    
    
        OpenAiChatModel chatModel = OpenAiChatModel.builder().apiKey("demo").baseUrl("http://langchain4j.dev/demo/openai/v1").modelName("gpt-4o-mini").build();
        System.out.println("response: "+chatModel.chat("你是谁?"));
    }


	@Autowired
	private OpenAiChatModel openAiChatModel;

    @Autowired
    private QwenChatModel qwenChatModel;

	@Test
    public void testSpringboot() {
        System.out.println("response: "+openAiChatModel.chat("你是谁?"));
    }

    /**
     * 千问
     */
    @Test
    public void qwenTest(){
        String chat = qwenChatModel.chat("你是谁");
        System.out.println(chat);
    }

    /**
     * 万象生成图片
     */
    @Test
    public void wanx(){

        WanxImageModel wanxImageModel = WanxImageModel.builder()
                    .modelName("wanx2.1-t2i-plus") 
                    .apiKey("sk-12f711f107544a5592a07c5ad1d4ef97")     
                    .build();
        Response<Image> response = wanxImageModel.generate("美女");
        System.out.println(response.content().url());
    }


    /**
     * aiService
     */
    @Test
    public void aiServiceTest(){

        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String chat = assistant.chat("你是谁");
        System.out.println(chat);
    }

    @Autowired
    private Assistant assistant;

    @Test
    public void aiService1(){

        String chat = assistant.chat("你是谁");
        System.out.println(chat);
    }


    /**
     * 聊天记忆简单版
     */
    @Test
    public void aiMemory(){
        QwenChatModel chatModel = QwenChatModel.builder().apiKey("sk-12f711f107544a5592a07c5ad1d4ef97")
        .modelName("qwen-max").build();

        UserMessage userMessage = UserMessage.userMessage("我是莎莎");
        ChatResponse aiResponse = chatModel.chat(userMessage);
        AiMessage aiMessage = aiResponse.aiMessage();
        System.out.println(aiMessage.text());

        UserMessage userMessage2 = UserMessage.userMessage("知道我是谁了吗");
        ChatResponse aiResponse2 = chatModel.chat(Arrays.asList(userMessage,aiMessage,userMessage2));
        AiMessage aiMessage2 = aiResponse2.aiMessage();
        System.out.println(aiMessage2.text());
    }

    /**
     * 聊天记忆
     */
    @Test
    public void aiMemory2(){

        MessageWindowChatMemory withMaxMessages = MessageWindowChatMemory.withMaxMessages(10);
        QwenChatModel chatModel = QwenChatModel.builder().apiKey("sk-12f711f107544a5592a07c5ad1d4ef97")
        .modelName("qwen-max").build();

        Assistant assistant = AiServices.builder(Assistant.class).chatModel(chatModel).chatMemory(withMaxMessages).build();
        String chat = assistant.chat("我是莎莎");
        System.out.println(chat);

        String chat2 = assistant.chat("你知道我是谁吗");
        System.out.println(chat2);
    }

    /**
     * 聊天记忆进阶版
     */
    @Test
    public void aiMemory3(){

        String chat = assistant.chat("我是莎莎");
        System.out.println(chat);
        String chat2 = assistant.chat("我是谁");
        System.out.println(chat2);
    }


    @Autowired
    private SeprateAssistant seprateMessage;

    /**
     * 隔离聊天
     */
    @Test
    public void seprateMessage(){

        String chat = seprateMessage.chat(1, "我是莎莎");
        System.out.println(chat);

        String chat2 = seprateMessage.chat(1, "我是谁");
        System.out.println(chat2);

        String chat3 = seprateMessage.chat(2, "我是谁");
        System.out.println(chat3);
       
    }

    @Autowired
    private SeprateAssistantMysql seprateAssistantMysql;

    @Test
    public void seprateMessageMysql(){

        String chat = seprateAssistantMysql.chat(1, "我是莎莎");
        System.out.println(chat);

        String chat2 = seprateAssistantMysql.chat(1, "我是谁");
        System.out.println(chat2);

        String chat3 = seprateAssistantMysql.chat(2, "我是谁");
        System.out.println(chat3);
       
    }

    @Test
    public void seprateMessageMysql2(){

        String chat = seprateAssistantMysql.chat(3, "我是莎莎");
        System.out.println(chat);
       
    }

    /**
     * 多业务参数
     */
    @Test
    public void seprateMessageMysql3(){

        String chat = seprateAssistantMysql.chat2(4, "我是谁","莎莎",36);
        System.out.println(chat);
       
    }

    /**
     * 多业务参数
     */
    @Test
    public void seprateMessageMysql4(){

        Person person = assistant.extractPerson("这个人的姓名是张三,年龄为26");
        System.out.println(person);
       
    }

    @Autowired
    private LegalAdvisor legalAdvisor;


     /**
     * 限定回答范围
     */
     @Test
     public void seprateMessage5(){
 
         LegalPrompt legalPrompt = new LegalPrompt();
         legalPrompt.setLegal("劳动法");
         legalPrompt.setQuestion("劳动法第58条是什么");
         String answerLegalQuestion = legalAdvisor.answerLegalQuestion(legalPrompt);
         System.out.println(answerLegalQuestion);
        
     }


     @Autowired
     private AssistantTool assistantTool;
      /**
     * 提供计算方法
     */
    @Test
    public void assistantTool(){

        String chat = assistantTool.chat("123+321等于多少");
        System.out.println(chat);
       
    }

    @Autowired
    private ProductAiService productAiService;
    
    @Test
    public void getProductDesc(){
        String chat = productAiService.chat("5", "苹果13");
        System.out.println(chat);
    } 
}
