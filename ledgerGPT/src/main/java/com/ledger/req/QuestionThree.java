package com.ledger.req;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@NoArgsConstructor
public class QuestionThree {

    public static String url="https://api.openai.com/v1/completions";
    private  String model="text-davinci-003";
    private  Integer max_tokens;
    private  double top_p;
    private  double temperature;
    private  double frequency_penalty;
    private  double presence_penalty;
    private  String prompt;



    public QuestionThree(String prompt,Integer max_tokens, double temperature,double presence_penalty, double frequency_penalty, double top_p) {
        this.max_tokens = max_tokens;
        this.top_p = top_p;
        this.temperature = temperature;
        this.frequency_penalty = frequency_penalty;
        this.presence_penalty = presence_penalty;
        this.prompt = prompt;
    }

}
