package com.jiawei.wu.utils;

import com.jiawei.wu.rpc.RpcProxy.RpcClientProxy;
import com.jiawei.wu.rpc.transmission.RpcClient;
import com.jiawei.wu.rpc.transmission.socket.client.SocketRpcClient;

public class ProxyUtils {
    private static final RpcClient rpcClient =new SocketRpcClient("127.0.0.1",8888);
    private static final RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
    public static <T> T getProxy(Class<T> clazz) {
        return rpcClientProxy.getProxy(clazz);
    }
}
