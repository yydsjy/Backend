package com.example.mall.exception;

public enum ExceptionEnum {
    NEED_USER_NAME(10001,"Null Username"),
    NEED_PASSWORD(10002,"Null Password"),
    PASSWORD_TOO_SHORT(10003,"Password should be longer than 8"),
    NAME_EXISTED(10004,"Duplicated Username"),
    INSERT_FAILED(10005,"Failed Insert"),
    WRONG_PASSWORD(10006,"WRONG PASSWORD"),
    NEED_LOGIN(10007,"Null User"),
    UPDATE_FAILED(10008,"Failed Update"),
    NEED_ADMIN(10009,"Not Administrator"),
    NEED_PARA(10010,"Null Parameter"),
    CREATE_FAILED(10011,"Failed Create"),
    REQUEST_PARA_ERROR(10012,"Wrong Parameter"),
    DELETE_FAILED(10013,"Failed Delete"),
    MKDIR_FAILED(10014,"Failed Make Dir"),
    UPLOAD_FAILED(10015,"Failed Upload"),
    NEED_PRODUCT(10016,"Null Product"),
    LACK_PRODUCT(10017,"Not Enough Product"),
    EMPTY_CART(10018,"Cart Is Empty"),
    UNFOUND_ENUM(10019,"Enum is unfound"),
    UNFOUND_ORDER(10020,"Order is unfound"),
    WRONG_ORDER(10021,"Order is Wrong"),
    WRONG_ORDER_STATUS(10022,"Order Status is Wrong"),
    SYSTEM_ERROR(20000,"System Error");

    Integer code;
    String msg;


    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
