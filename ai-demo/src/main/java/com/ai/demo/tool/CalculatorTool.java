package com.ai.demo.tool;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

@Component
public class CalculatorTool {

    @Tool(name="add",value = "加法计算")
    public BigDecimal add(@P(value = "加数1",required = true) BigDecimal a,@P(value = "加数2",required = true) BigDecimal b){

        System.out.println("发放调用");
        return a.add(b);
    }
}
