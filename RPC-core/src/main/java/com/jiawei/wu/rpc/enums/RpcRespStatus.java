package com.jiawei.wu.rpc.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum RpcRespStatus {
    SUCCESS(2000,"success"),
    FAIL(11000,"fail")
    ;

    private final int code;
    private final String msg;

    public static boolean isSuccess(Integer code){
        return SUCCESS.getCode() == code;
    }
    public static boolean isFail(Integer code){
        return FAIL.getCode() == code;
    }
}
