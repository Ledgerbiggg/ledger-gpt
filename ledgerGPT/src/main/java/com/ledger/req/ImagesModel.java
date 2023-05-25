package com.ledger.req;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@NoArgsConstructor
public class ImagesModel {
    /*
    *     "model": "image-alpha-001",
    *     "prompt": "我要一张美女照片",
    *     "size": "1024x1024"
    * */
    public static String url="https://api.openai.com/v1/images/generations";
    private String model="image-alpha-001";
    private String prompt;
    private String size="1024x1024";
    private Integer n=1;


    public ImagesModel(String prompt,Integer n) {
        this.prompt = prompt;
        this.n=n;
    }
}
