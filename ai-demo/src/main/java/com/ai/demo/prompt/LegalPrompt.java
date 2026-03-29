package com.ai.demo.prompt;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Data;

/**
 * 限定回答范围
 */
@Data
@StructuredPrompt("根据中国{{legal}}法律，解答一下问题，{{question}}")
public class LegalPrompt {

    private String legal;

    private String question;
}
