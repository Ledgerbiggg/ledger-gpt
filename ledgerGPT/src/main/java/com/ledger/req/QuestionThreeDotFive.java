package com.ledger.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@NoArgsConstructor
public class QuestionThreeDotFive {
    public static String url = "https://api.openai.com/v1/chat/completions";
    private String model = "gpt-3.5-turbo";
    private ArrayList<Map<String, String>> messages = new ArrayList<>();
    private double temperature;
    private double top_p;
    private double frequency_penalty;
    private double presence_penalty;

    public QuestionThreeDotFive(List<Map<String, String>> msgList,Double temperature,Double presence_penalty,Double frequency_penalty,Double top_p) {
        for (int i = 0; i < msgList.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            Map<String, String> stringStringMap = msgList.get(i);
            if (i % 2 == 0) {
                hashMap.put("role", "user");
                hashMap.put("content", stringStringMap.get("user"));
            } else {
                hashMap.put("role", "system");
                hashMap.put("content", stringStringMap.get("system"));
            }
            messages.add(hashMap);
        }
        this.temperature = temperature;
        this.presence_penalty = presence_penalty;
        this.frequency_penalty = frequency_penalty;
        this.top_p = top_p;
    }

}
