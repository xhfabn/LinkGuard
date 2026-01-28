package com.jiawei.wu.utils;

import com.jiawei.wu.rpc.RpcProxy.RpcClientProxy;
import com.jiawei.wu.rpc.factory.SingletonFactory;
import com.jiawei.wu.rpc.transmission.RpcClient;
import com.jiawei.wu.rpc.transmission.socket.client.SocketRpcClient;

public class ProxyUtils {
    private static final RpcClient rpcClient =SingletonFactory.getInstance(SocketRpcClient.class);
    private static final RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
    public static <T> T getProxy(Class<T> clazz) {
        return rpcClientProxy.getProxy(clazz);
    }
}
