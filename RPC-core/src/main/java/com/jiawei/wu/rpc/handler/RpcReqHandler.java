package com.jiawei.wu.rpc.handler;

import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.provider.ServiceProvider;
import lombok.SneakyThrows;

public class RpcReqHandler {
    private final ServiceProvider serviceProvider;

    public RpcReqHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @SneakyThrows
    public Object invoke(RpcReq rpcReq) {
        String rpcServiceName = rpcReq.rpcServiceName();
        Object service = serviceProvider.getService(rpcServiceName);

        java.lang.reflect.Method method = service.getClass().getMethod(rpcReq.getMethodName(), rpcReq.getParamTypes());

        return method.invoke(service, rpcReq.getParams());
    }
}
