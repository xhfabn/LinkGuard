package com.jiawei.wu.rpc.registry;

import com.jiawei.wu.rpc.dto.RpcReq;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {
    InetSocketAddress lookupService(RpcReq rpcReq);
}
