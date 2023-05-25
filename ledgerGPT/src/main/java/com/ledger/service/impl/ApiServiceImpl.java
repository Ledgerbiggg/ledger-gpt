package com.ledger.service.impl;

import com.ledger.common.SubmitUtil;
import com.ledger.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author ledger
 * @version 1.0
 **/
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private SubmitUtil submitUtil;


    @Override
    public String submitThreeDotFive(List<Map<String, String>> msgList, Map<String, Object> params) throws IOException {
      return  submitUtil.submitQuestionThreeDotFive(msgList,params);
    }

    @Override
    public String submitThree(List<Map<String, String>> msgList, Map<String, Object> params) throws IOException {
        return submitUtil.submitQuestionThree(msgList,params);
    }

    @Override
    public List<String> toGetImage(Map<String, String> promptMap, Map<String, Object> params) throws IOException {
        return submitUtil.submitImageModel(promptMap,params);
    }


}
