package com.ledger.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@TableName("chat_info")
public class ChatInfo {
    @TableId(value = "id")
    private Long id;
    private String msg;
    private Boolean haveChat;
    private Integer havePaymentCode;
    private Boolean chat;
    private Integer maxKey;
    private String userId;
    private Integer current;

}
