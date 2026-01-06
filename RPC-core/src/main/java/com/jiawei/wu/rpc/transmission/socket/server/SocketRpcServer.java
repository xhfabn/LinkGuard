package com.jiawei.wu.rpc.transmission.socket.server;

import com.jiawei.wu.rpc.config.RpcServiceConfig;
import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.handler.RpcReqHandler;
import com.jiawei.wu.rpc.provider.ServiceProvider;
import com.jiawei.wu.rpc.provider.impl.SimpleServiceProvider;
import com.jiawei.wu.rpc.transmission.RpcServer;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SocketRpcServer implements RpcServer {
    private final int port;
    private final RpcReqHandler rpcReqHandle;
    private final ServiceProvider serviceProvider;


    public SocketRpcServer(int port) {
        this(port,new SimpleServiceProvider());
    }

    public SocketRpcServer(int port, ServiceProvider serviceProvider) {
        this.port = port;
        this.serviceProvider = serviceProvider;
        this.rpcReqHandle = new RpcReqHandler(serviceProvider);
    }

    @Override
    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务启动, 端口: " + port);

            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                RpcReq rpcReq = (RpcReq) inputStream.readObject();
                System.out.println(rpcReq);

                //  假装调用了rpcReq中的接口实现类的方法
                String data = "sfsdf12312";

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                RpcResp<String> rpcResp = RpcResp.success(rpcReq.getReqId(), data);
                outputStream.writeObject(rpcResp);
                outputStream.flush();
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
