package com.ledger.controller;

import com.ledger.common.Result;
import com.ledger.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
@RequestMapping("/oss")
@Slf4j
public class OSSController {

    @Resource
    private OssService ossService;

    @GetMapping("/getSign/{type}")
    public Result<Map<String, String>> getSign(@PathVariable Long type) {
        return ossService.getSign(type);
    }
}

