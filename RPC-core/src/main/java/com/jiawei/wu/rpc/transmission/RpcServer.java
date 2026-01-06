package com.jiawei.wu.rpc.transmission;

import com.jiawei.wu.rpc.config.RpcServiceConfig;

public interface RpcServer {
    void start();

    void publishService(RpcServiceConfig config);
}
