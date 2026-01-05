package com.jiawei.wu.dto;

import com.jiawei.wu.enums.RpcRespStatus;
import lombok.Data;

@Data
public class RpcResp<T> {
    private static final long serialVersionUID = 1L;

    private String reqId;
    private Integer code;
    private String msg;
    private T data;

    public static <T> RpcResp<T> success(String reqId, T data) {
        RpcResp<T> resp = new RpcResp<T>();
        resp.setReqId(reqId);
        resp.setCode(0);
        resp.setData(data);

        return resp;
    }

    public static <T> RpcResp<T> fail(String reqId, RpcRespStatus status) {
        RpcResp<T> resp = new RpcResp<T>();
        resp.setReqId(reqId);
        resp.setCode(status.getCode());
        resp.setMsg(status.getMsg());
        return resp;
    }
    public static <T> RpcResp<T> fail(String reqId, String msg) {
        RpcResp<T> resp = new RpcResp<T>();
        resp.setReqId(reqId);
        resp.setCode(RpcRespStatus.FAIL.getCode());
        resp.setMsg(msg);

        return resp;
    }

}
