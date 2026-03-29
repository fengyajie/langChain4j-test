package com.ai.demo.product;

import lombok.Data;

@Data
public class ProductInfo {

    private String productName;

    private String desc;

    public ProductInfo(String productName,String desc){

        this.productName = productName;
        this.desc=desc;
    }
}
