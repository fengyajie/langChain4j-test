package com.ai.demo.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.Tool;

@Component
public class ProductTool {

    public static final Map<String,ProductInfo> productMap = new HashMap<>();

    static{
        productMap.put("苹果13",new ProductInfo("苹果13","苹果13黑色手机"));
        productMap.put("苹果14",new ProductInfo("苹果14","苹果14蓝色手机"));
    }
    @Tool
    public String getProductInfo(String productName){

        ProductInfo productInfo = productMap.get(productName);
        if(Objects.isNull(productInfo)){

            return "";
        }
        return "商品是"+productInfo.getDesc();
    }
}
