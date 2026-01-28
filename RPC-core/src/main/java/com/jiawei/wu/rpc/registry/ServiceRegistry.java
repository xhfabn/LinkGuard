package com.jiawei.wu.rpc.registry;

import java.net.InetSocketAddress;

public interface ServiceRegistry {
    void registerService(String rpcServiceName, InetSocketAddress address);
}
