package io.recommendation.common.vo;

public enum Code {

    SUCCESS(200,"success"),
    SERVER_ERROR(500,"error"),
    PARAM_ILLEGAL(400,"param is illegal"),
    AUTHORIZED(90001,"have not login"),
    NOT_FOUND_USER(90002,"not found this user"),
    PASSWORD_ERROR(90003,"password is error");


    int code;
    String msg;

    Code(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
