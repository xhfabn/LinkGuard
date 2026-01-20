package com.jiawei.wu.rpc.RpcProxy;

import cn.hutool.core.util.IdUtil;
import com.jiawei.wu.rpc.config.RpcServiceConfig;
import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.transmission.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcClientProxy implements InvocationHandler {
    private final RpcClient rpcClient;
    private final RpcServiceConfig rpcServiceConfig;

    public RpcClientProxy(RpcClient rpcClient) {
        this(rpcClient, new RpcServiceConfig());
    }

    public RpcClientProxy(RpcClient rpcClient, RpcServiceConfig config) {
        this.rpcClient = rpcClient;
        this.rpcServiceConfig = config;
    }
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class<?>[]{clazz},
            this
        );
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         RpcReq rpcReq = RpcReq.builder()
            .reqId(IdUtil.fastSimpleUUID())
            .interfaceName(method.getDeclaringClass().getName())
            .methodName(method.getName())
            .params(args)
            .paramTypes(method.getParameterTypes())
            .group(rpcServiceConfig.getGroup())
            .version(rpcServiceConfig.getVersion())
            .build();

        RpcResp<?> rpcResp = rpcClient.sendReq(rpcReq);
        check(rpcReq, rpcResp);
        return rpcResp.getData();
    }
    public void check(RpcReq rpcReq, RpcResp<?> rpcResp) {
        if (rpcResp == null) {
            throw new RuntimeException("rpc resp is null");
        }
        if (!rpcReq.getReqId().equals(rpcResp.getReqId())) {
            throw new RuntimeException("req id and resp id not match");
        }
        if (rpcResp.getCode() != 0) {
            throw new RuntimeException("rpc resp code is not 0, message: " + rpcResp.getMsg());
        }
    }
}
