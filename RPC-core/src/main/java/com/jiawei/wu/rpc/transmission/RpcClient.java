package com.jiawei.wu.rpc.transmission;

import com.jiawei.wu.rpc.dto.RpcReq;
import com.jiawei.wu.rpc.dto.RpcResp;

public interface RpcClient {
    RpcResp<?> sendReq(RpcReq req);
}
