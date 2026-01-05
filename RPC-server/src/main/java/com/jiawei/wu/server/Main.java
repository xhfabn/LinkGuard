package com.jiawei.wu.server;

import com.jiawei.wu.transmission.RpcServer;
import com.jiawei.wu.transmission.socket.server.SocketRpcServer;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        RpcServer rpcServer = new SocketRpcServer(8888);
        rpcServer.start();
    }
}