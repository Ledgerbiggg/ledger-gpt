package com.ledger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.common.Result;
import com.ledger.domain.BarList;
import com.ledger.domain.dto.BarListDTO;

/**
 * @author ledger
 * @version 1.0
 **/
public interface BarListService extends IService<BarList> {
    Result<String> add(BarListDTO barList);

    Result<String> edit(String oldValue, String newValue);
}
