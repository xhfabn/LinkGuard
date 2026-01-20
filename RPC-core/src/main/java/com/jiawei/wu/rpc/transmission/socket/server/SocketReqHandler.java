package com.jiawei.wu.rpc.transmission.socket.server;


import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;
import com.jiawei.wu.rpc.handler.RpcReqHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
@AllArgsConstructor
public class SocketReqHandler implements Runnable {
    private final Socket socket;
    private final RpcReqHandler rpcReqHandler;

   @SneakyThrows
    @Override
    public void run() {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
       RpcReq rpcReq = (RpcReq) inputStream.readObject();

         Object result = rpcReqHandler.invoke(rpcReq);

         ObjectOutputStream outputStream= new ObjectOutputStream(socket.getOutputStream());
         RpcResp<?> rpcResp=RpcResp.success(rpcReq.getReqId(), result);
         outputStream.writeObject(rpcResp);
         outputStream.flush();
   }
}
