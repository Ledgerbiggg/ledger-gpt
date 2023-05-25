package com.ledger.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
@TableName("user")
public class User {
    @TableId(value = "id")
    private String id;
    private String name;
    private Integer gender;
    private Integer age;
    private String description;
    private String avatarUrl;
    private String backgroundImageUrl;

}
