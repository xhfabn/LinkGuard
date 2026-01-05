package com.jiawei.wu.transmission.socket.server;

import com.jiawei.wu.dto.RpcReq;
import com.jiawei.wu.dto.RpcResp;
import com.jiawei.wu.transmission.RpcServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketRpcServer implements RpcServer {
    private final int port;
    public SocketRpcServer(int port) {
        this.port = port;
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
}
