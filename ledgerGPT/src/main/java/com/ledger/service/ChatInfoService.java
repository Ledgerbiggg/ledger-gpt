package com.ledger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.common.Result;
import com.ledger.domain.ChatInfo;
import com.ledger.domain.dto.ChatInfoDTO;

/**
 * @author ledger
 * @version 1.0
 **/
public interface ChatInfoService extends IService<ChatInfo> {

    Result<ChatInfoDTO> getInfo();

    Result<String> saveInfo(ChatInfoDTO chatInfoDTO);

    Result<String> updateCurrent(ChatInfo current);
}
