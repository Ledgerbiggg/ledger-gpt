package com.ledger.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ledger.domain.ChatInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Base64;

/**
 * @author ledger
 * @version 1.0
 **/
@Mapper
public interface ChatInfoMapper extends BaseMapper<ChatInfo> {
}
