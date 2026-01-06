package com.jiawei.wu.rpc.transmission.socket.client;

import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.transmission.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class SocketRpcClient implements RpcClient {
    private final String host;
    private final int port;
    public SocketRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public RpcResp<?> sendReq(RpcReq req) {
        try(Socket socket = new Socket(host,port)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(req);
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
