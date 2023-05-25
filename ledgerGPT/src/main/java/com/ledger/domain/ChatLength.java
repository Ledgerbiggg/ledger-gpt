package com.ledger.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@TableName("chat_length")
public class ChatLength {
    @TableId(value = "id")
    private Long id;
    private Integer chatKey;
    private String content;
    private String userId;
    private Integer sequence;
}
