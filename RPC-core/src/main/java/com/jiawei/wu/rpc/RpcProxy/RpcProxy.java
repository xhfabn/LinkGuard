package com.jiawei.wu.rpc.RpcProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcProxy<T> implements InvocationHandler {
    private String serverIp;
    private int serverPort;

    public RpcProxy(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    //生成代理对象的方法
    @SuppressWarnings("unchecked")
    public T getProxy(Class<T> serviceInterface) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(
                serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
