package com.ledger.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value =ApiErrorException.class)
    @ResponseBody
    public Result<String> exceptionHandler(ApiErrorException e){
        if(e.getE()!=null){
            log.error(e.getE().getMessage());
            return Result.success(e.getE().getMessage());
        }
        log.error(e.getMessage());
        return Result.success(e.getErrorMsg());
    }
}
