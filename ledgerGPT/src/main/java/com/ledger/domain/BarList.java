package com.ledger.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@TableName("bar_list")
public class BarList {
    @TableId(value = "id")
    private Long id;
    private String bar;
    private String userId;
    private Integer sequence;
}
