package com.ledger.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.ledger.common.ApiErrorException;
import com.ledger.common.Result;
import com.ledger.service.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ledger
 * @version 1.0
 **/
@Service
public class OssServiceImpl implements OssService {

    @Value("${oss.accessId}")
    private String accessId;
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.bucketName}")
    private String bucketName;

    @Override
    public Result<Map<String, String>> getSign(Long type) {
        String host = "https://" + bucketName + "." + endpoint;
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        String dir = null;
        if (type == 0) {
            dir = "backgroundImage/";
        } else if (type == 1) {
            dir = "avatar/";
        }
        // 创建ossClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
        try {
            //十年过期的url
            long expireEndTime = System.currentTimeMillis() + 3600l * 1000 * 24 * 365 * 10;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

            return Result.success(respMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new ApiErrorException("上传文件失败");
    }
}
