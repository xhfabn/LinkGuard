package com.jiawei.wu.rpc.provider.impl;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import com.jiawei.wu.rpc.config.RpcServiceConfig;
import com.jiawei.wu.rpc.constant.RpcConstants;
import com.jiawei.wu.rpc.factory.SingletonFactory;
import com.jiawei.wu.rpc.provider.ServiceProvider;
import com.jiawei.wu.rpc.registry.ServiceRegistry;
import com.jiawei.wu.rpc.registry.impl.ZkServiceRegistry;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ZkServiceProvider implements ServiceProvider {

    private final Map<String, Object> SERVICE_CACHE=new HashMap<>();
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProvider() {
        this(SingletonFactory.getInstance(ZkServiceRegistry.class));
    }

    public ZkServiceProvider(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void publishService(RpcServiceConfig config) {
        config.rpcServiceNames()
            .forEach(rpcServiceName->publishService(rpcServiceName,config.getService()));
    }

    @Override
    public Object getService(String rpcServiceName) {
        if(StrUtil.isBlank(rpcServiceName)){
            throw new IllegalArgumentException("rpcServiceName为空");
        }

        if(!SERVICE_CACHE.containsKey(rpcServiceName)){
            throw new IllegalArgumentException("rpcServiceName未注册: "+rpcServiceName);
        }
        return SERVICE_CACHE.get(rpcServiceName);
    }
    @SneakyThrows
    private void publishService(String rpcServiceName,Object service) {
        String host=InetAddress.getLocalHost().getHostAddress();
        int port=RpcConstants.SERVER_PORT;

        InetSocketAddress address=new InetSocketAddress(host, port);
        serviceRegistry.registerService(rpcServiceName,address);

        SERVICE_CACHE.put(rpcServiceName,service);
    }
}
