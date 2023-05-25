package com.ledger.controller;

import com.ledger.common.ApiErrorException;
import com.ledger.common.Result;
import com.ledger.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/getAnsThreeDotFive")
    public Result<String> getRespThreeDotFive(@RequestBody Map questionMap){
        try {
            //配置基本参数["xxx","xxx"]
            ArrayList<String> question = (ArrayList<String>)questionMap.get("question");
            //配置额外参数
            Map<String, Object> params = (Map<String, Object>) questionMap.get("params");
            //数组存放对话格式是[{"user":"xxx"},{"system":"xxx"}]
            ArrayList<Map<String,String>> msgList = new ArrayList<>();
            for (int i = 0; i < question.size(); i++) {
                HashMap<String, String> msgMap = new HashMap<>();
                if(i % 2==0){
                    msgMap.put("user",question.get(i));
                }else {
                    msgMap.put("system",question.get(i));
                }
                msgList.add(msgMap);
            }
            return Result.success(apiService.submitThreeDotFive(msgList,params));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiErrorException("请求出现异常，请联系管理员vx：Ledgerbiggg",e);
        }
    }
    @PostMapping("/getAnsThree")
    public Result<String> getRespThree(@RequestBody Map questionMap){
        try {
            //配置基本参数
            String prompt = (String) questionMap.get("prompt");
            ArrayList<Map<String, String>> prompts = new ArrayList<>();
            //配置额外参数
            Map<String, Object> params = (Map<String, Object>) questionMap.get("params");
            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap.put("prompt",prompt);
            prompts.add(stringStringMap);
            return Result.success(apiService.submitThree(prompts,params));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiErrorException("请求出现异常，请联系管理员vx：Ledgerbiggg",e);
        }
    }

    @PostMapping("/getImage")
    public Result<List<String>> getImage(@RequestBody Map questionMap){
        Map<String, Object> params = (Map<String, Object>) questionMap.get("params");
        try {
            return Result.success(apiService.toGetImage(questionMap,params));
        } catch (IOException e) {
            throw new ApiErrorException("请求出现异常，请联系管理员vx：Ledgerbiggg",e);
        }
    }

}
