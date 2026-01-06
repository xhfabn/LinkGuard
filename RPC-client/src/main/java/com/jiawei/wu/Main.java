package com.jiawei.wu;

import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.transmission.RpcClient;
import com.jiawei.wu.rpc.transmission.socket.client.SocketRpcClient;

public class Main {
    public static void main(String[] args) {
        RpcClient rpcClient = new SocketRpcClient("localhost", 8888);

        RpcReq req= RpcReq.builder()
                .reqId("1213")
                .interfaceName("com.jiawei.wu.api.UserService")
                .methodName("getUser")
                .params(new Object[]{1L})
                .paramTypes(new Class[]{Long.class})
                .build();

        RpcResp<?> rpcResp = rpcClient.sendReq(req);
        System.out.println(rpcResp.getData());

    }
}