package com.ledger.service;

import com.ledger.common.Result;

import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
public interface OssService {
    Result<Map<String, String>> getSign(Long type);
}
