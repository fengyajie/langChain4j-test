package com.ai.demo.domain.vo;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
public class Person {

    @Description("用户的姓名")
    private String name;

    @Description("用户的年龄")
    private Integer age;
}
