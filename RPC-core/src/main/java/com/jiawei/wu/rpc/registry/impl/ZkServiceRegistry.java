package com.jiawei.wu.rpc.registry.impl;

import cn.hutool.core.util.StrUtil;
import com.jiawei.wu.rpc.constant.RpcConstants;
import com.jiawei.wu.rpc.factory.SingletonFactory;
import com.jiawei.wu.rpc.registry.ServiceRegistry;
import com.jiawei.wu.rpc.registry.ZK.ZkClient;
import com.jiawei.wu.rpc.util.IPUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class ZkServiceRegistry implements ServiceRegistry {
    private final ZkClient zkClient;

    public ZkServiceRegistry() {
        this.zkClient = SingletonFactory.getInstance(ZkClient.class);
    }

    public ZkServiceRegistry(ZkClient zkClient) {
        this.zkClient = zkClient;
    }
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress address) {
        log.info("服务注册 {} at {}", rpcServiceName, address);
        String path= RpcConstants.ZK_RPC_ROOT_PATH
                + StrUtil.SLASH
                + rpcServiceName
                + StrUtil.SLASH
                + IPUtils.toIpPort(address);
        zkClient.createPersistentNode(path);
    }
}
