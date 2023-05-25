package com.ledger.common;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author ledger
 * @version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected Exception e;
    /**
     * 错误信息
     */
    protected String errorMsg;


    public ApiErrorException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public ApiErrorException(String errorMsg, Exception e) {
        this.errorMsg = errorMsg;
        this.e=e;
    }
}
