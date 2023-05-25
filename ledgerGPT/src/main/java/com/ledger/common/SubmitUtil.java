package com.ledger.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ledger.req.ImagesModel;
import com.ledger.req.QuestionThree;
import com.ledger.req.QuestionThreeDotFive;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author ledger
 * @version 1.0
 **/
@Component
@Slf4j
public class SubmitUtil {
    @Value("${keys}")
    private List<String> keys;
    @Autowired
    private QuestionFactory questionFactory;

    public String submitQuestionThree(List<Map<String, String>> msgList, Map<String, Object> params) throws IOException {
        return (String) getHttpResp(msgList, params, QuestionThree.class);
    }

    public String submitQuestionThreeDotFive(List<Map<String, String>> msgList, Map<String, Object> params) throws IOException {
        return (String)getHttpResp(msgList,params, QuestionThreeDotFive.class);
    }

    public List<String> submitImageModel(Map<String, String> promptMap, Map<String, Object> params) throws IOException {
        ArrayList<Map<String, String>> maps = new ArrayList<>();
        maps.add(promptMap);
        return (List<String>)getHttpResp(maps, params, ImagesModel.class);
    }

    //分开请求，返回数据对应处理
    private Object getHttpResp(List<Map<String, String>> msgList, Map<String, Object> params, Class clazz) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = null;
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Field field = clazz.getDeclaredField("url");
            field.setAccessible(true);
            httpPost = new HttpPost((String) field.get(instance));
        } catch (InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        httpPost.setHeader("Connection", "Close");
        int i = new Random().nextInt(keys.size());
        httpPost.setHeader("Authorization", "Bearer " + keys.get(i));
        if (clazz == QuestionThree.class) {
            Double temperature = Double.valueOf(params.get("temperature").toString());
            Double presence_penalty = Double.valueOf(params.get("presence_penalty").toString());
            Double frequency_penalty = Double.valueOf(params.get("frequency_penalty").toString());
            Double top_p = Double.valueOf(params.get("top_p").toString());
            QuestionThree answer = questionFactory.getQuestionThree(msgList.get(0).get("prompt"),temperature,presence_penalty,frequency_penalty,top_p);
            Map<String, Object> map = sendRes(answer, httpPost, httpClient);
            List choices = (List) map.get("choices");
            Map choicesMap = (Map) choices.get(0);
            return (String) choicesMap.get("text");
        } else if (clazz == QuestionThreeDotFive.class) {
            Double temperature = Double.valueOf(params.get("temperature").toString());
            Double presence_penalty = Double.valueOf(params.get("presence_penalty").toString());
            Double frequency_penalty = Double.valueOf(params.get("frequency_penalty").toString());
            Double top_p = Double.valueOf(params.get("top_p").toString());
            QuestionThreeDotFive answer = questionFactory.getQuestionThreeDotFive(msgList,temperature,presence_penalty,frequency_penalty,top_p);
            Map<String, Object> parse = sendRes(answer, httpPost, httpClient);
            List choices = (List) parse.get("choices");
            Map choicesMap = (Map) choices.get(0);
            Map message = (Map) choicesMap.get("message");
            return (String) message.get("content");
        } else if (clazz == ImagesModel.class) {
            Map<String, String> stringStringMap = msgList.get(0);
            Integer n = (Integer)params.get("n");
            String prompt = stringStringMap.get("prompt");
            ImagesModel imagesModel = questionFactory.getImagesModel(prompt,n);
            Map<String, Object> map = sendRes(imagesModel, httpPost, httpClient);
            List<Map<String,String>> datas = (List<Map<String,String>>) map.get("data");
            ArrayList<String> list = new ArrayList<>();
            for (Map<String, String> data : datas) {
                list.add(data.get("url"));
            }
            return list;
        }
        throw new ApiErrorException("出现错误，请联系vx:Ledgerbiggg");
    }
    //通用的请求方法
    private Map<String, Object> sendRes(Object reqBean, HttpPost httpPost, CloseableHttpClient httpClient) throws IOException {
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(reqBean), StandardCharsets.UTF_8);
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            Map<String, Object> parse = (Map<String, Object>) JSON.parse(sb.toString());
            return parse;
        } else {
            log.error("请求失败");
            log.error("清清状态马:" + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8));
            String s = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = br.readLine()) != null) {
                stringBuilder.append(s);
            }
            String errMsg = stringBuilder.toString();
            JSONObject jsonObject = JSON.parseObject(errMsg);

            Map<String, Object> error = jsonObject.getInnerMap();
            Map errMap = (Map)error.get("error");
            String message = (String)errMap.get("message");
            log.error(message);
            throw new ApiErrorException("出现错误，请联系vx:Ledgerbiggg",new RuntimeException(message));
        }
    }
}
