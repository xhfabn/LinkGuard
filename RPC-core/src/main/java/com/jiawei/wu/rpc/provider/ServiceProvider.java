package com.jiawei.wu.rpc.provider;

import com.jiawei.wu.rpc.config.RpcServiceConfig;

public interface ServiceProvider {
    void publishService(RpcServiceConfig config);

    Object getService(String rpcServiceName);
}
