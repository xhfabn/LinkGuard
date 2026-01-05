package com.jiawei.wu.transmission.socket.client;

import com.jiawei.wu.dto.RpcReq;
import com.jiawei.wu.dto.RpcResp;
import com.jiawei.wu.transmission.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class socketRpcClient implements RpcClient {
    @Override
    public RpcResp<?> sendReq(RpcReq req) {
        try(Socket socket = new Socket("127.0.0.1",8888)) {
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
