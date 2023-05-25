package com.ledger.common;

import com.ledger.req.ImagesModel;
import com.ledger.req.QuestionThree;
import com.ledger.req.QuestionThreeDotFive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@Component
public class QuestionFactory {

    @Value("${questionThree.max_tokens}")
    private Integer max_tokens;


    public QuestionThree getQuestionThree(String prompt,Double temperature,Double presence_penalty,Double frequency_penalty,Double top_p) {
        return new QuestionThree(prompt,max_tokens,temperature,presence_penalty, frequency_penalty, top_p);
    }

    public QuestionThreeDotFive getQuestionThreeDotFive(List<Map<String,String>> msgList,Double temperature,Double presence_penalty,Double frequency_penalty,Double top_p) {
        return new QuestionThreeDotFive(msgList, temperature,presence_penalty, frequency_penalty,top_p );
    }

    public ImagesModel getImagesModel(String prompt,Integer n){

        return new ImagesModel(prompt,n);
    }


}
