package com.jiawei.wu.server;

import com.jiawei.wu.rpc.config.RpcServiceConfig;
import com.jiawei.wu.rpc.transmission.RpcServer;
import com.jiawei.wu.rpc.transmission.socket.server.SocketRpcServer;
import com.jiawei.wu.server.service.NativeServiceImpl;

public class Main {
    public static void main(String[] args) {
        RpcServiceConfig config = new RpcServiceConfig(new NativeServiceImpl());
        RpcServer rpcServer = new SocketRpcServer();
        rpcServer.publishService(config);

        rpcServer.start();
    }
}