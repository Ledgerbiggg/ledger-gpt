package com.ledger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.common.Result;
import com.ledger.domain.ChatLength;
import com.ledger.domain.dto.ChatInfoDTO;

/**
 * @author ledger
 * @version 1.0
 **/
public interface ChatLengthService extends IService<ChatLength> {
    Result<String> saveChat(ChatInfoDTO chatInfoDTO);

    Result<String> delete(String bar, Integer current);

    Result<String> deleteAll();
}
