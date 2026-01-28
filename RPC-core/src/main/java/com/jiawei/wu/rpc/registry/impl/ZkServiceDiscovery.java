package com.jiawei.wu.rpc.registry.impl;

import cn.hutool.core.util.StrUtil;
import com.jiawei.wu.rpc.LoadBalance.LoadBalance;
import com.jiawei.wu.rpc.LoadBalance.impl.RandomLoadBalance;
import com.jiawei.wu.rpc.constant.RpcConstants;
import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.factory.SingletonFactory;
import com.jiawei.wu.rpc.registry.ServiceDiscovery;
import com.jiawei.wu.rpc.registry.ZK.ZkClient;
import com.jiawei.wu.rpc.util.IPUtils;

import java.net.InetSocketAddress;
import java.util.List;

public class ZkServiceDiscovery implements ServiceDiscovery {
    private final ZkClient zkClient;
    private final LoadBalance loadBalance;

    public ZkServiceDiscovery() {
        this(
                SingletonFactory.getInstance(ZkClient.class),
                SingletonFactory.getInstance(RandomLoadBalance.class)
        );
    }
    public ZkServiceDiscovery(ZkClient zkClient, LoadBalance loadBalance) {
        this.zkClient = zkClient;
        this.loadBalance = loadBalance;
    }

    @Override
    public InetSocketAddress lookupService(RpcReq rpcReq) {
        String rpcServiceName= rpcReq.rpcServiceName();
        String path= RpcConstants.ZK_RPC_ROOT_PATH
                + StrUtil.SLASH
                + rpcServiceName;
        List<String> children=zkClient.getChildrenNodes(path);
        String address=loadBalance.select(children);
        return IPUtils.toInetSocketAddress(address);
    }
}
