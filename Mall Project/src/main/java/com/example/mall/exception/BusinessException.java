package com.example.mall.exception;

public class BusinessException extends RuntimeException {
    private final Integer code;
    private final String msg;

    public BusinessException(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ExceptionEnum ex){
        this(ex.getCode(),ex.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
