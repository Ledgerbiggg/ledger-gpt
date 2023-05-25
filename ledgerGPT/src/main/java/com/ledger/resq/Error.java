package com.ledger.resq;

import lombok.Data;

/**
 * @author ledger
 * @version 1.0
 **/
@Data
public class Error {
    private String code;
    private String message;
    private String param;
    private String type;

}
