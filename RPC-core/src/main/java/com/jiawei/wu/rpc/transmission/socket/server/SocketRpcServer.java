package com.jiawei.wu.rpc.transmission.socket.server;

import com.jiawei.wu.rpc.config.RpcServiceConfig;
import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.handler.RpcReqHandler;
import com.jiawei.wu.rpc.provider.ServiceProvider;
import com.jiawei.wu.rpc.provider.impl.SimpleServiceProvider;
import com.jiawei.wu.rpc.transmission.RpcServer;
import com.jiawei.wu.rpc.util.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Slf4j
public class SocketRpcServer implements RpcServer {
    private final int port;
    private final RpcReqHandler rpcReqHandle;
    private final ServiceProvider serviceProvider;
    private final ExecutorService executor;


    public SocketRpcServer(int port) {
        this(port,new SimpleServiceProvider());
    }

    public SocketRpcServer(int port, ServiceProvider serviceProvider) {
        this.port = port;
        this.serviceProvider = serviceProvider;
        this.rpcReqHandle = new RpcReqHandler(serviceProvider);
        this.executor= ThreadPoolUtils.createIoIntensiveThreadPool("socket-rpc-server-pool");
    }

    @Override
    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务启动, 端口: " + port);

            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                executor.submit(new SocketReqHandler(socket, rpcReqHandle));
            }
        } catch (Exception e) {
            System.err.println("服务端异常" + e);
        }
    }

    @Override
    public void publishService(RpcServiceConfig config) {
        serviceProvider.publishService(config);
    }


}
