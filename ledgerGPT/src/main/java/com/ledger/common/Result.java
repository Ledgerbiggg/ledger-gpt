package com.ledger.common;

import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
public class Result<T>{
    private Integer code;
    private String msg;
    private T data;

    //正确返回
    static public <T> Result<T> success(T t){
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setData(t);
        return result;
    }
    //错误返回
    static public  Result<String> error(String msg){
        Result<String> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }








}
