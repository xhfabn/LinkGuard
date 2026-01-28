package com.jiawei.wu.rpc.transmission.socket.client;

import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.factory.SingletonFactory;
import com.jiawei.wu.rpc.registry.ServiceDiscovery;
import com.jiawei.wu.rpc.registry.impl.ZkServiceDiscovery;
import com.jiawei.wu.rpc.transmission.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class SocketRpcClient implements RpcClient {
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this(SingletonFactory.getInstance(ZkServiceDiscovery.class));
    }
    public SocketRpcClient(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public RpcResp<?> sendReq(RpcReq rpcReq) {
        InetSocketAddress address = serviceDiscovery.lookupService(rpcReq);
        String host = address.getHostString();
        int port = address.getPort();
        try(Socket socket = new Socket(host,port)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcReq);
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object o = inputStream.readObject();

            return (RpcResp<?>) o;
        } catch (Exception e) {
            log.error("发送rpc请求失败", e);
            throw new RuntimeException(e);
        }
    }
}
