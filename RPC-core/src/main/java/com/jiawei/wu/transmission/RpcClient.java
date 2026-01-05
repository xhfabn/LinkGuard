package com.jiawei.wu.transmission;

import com.jiawei.wu.dto.RpcReq;
import com.jiawei.wu.dto.RpcResp;

public interface RpcClient {
    RpcResp<?> sendReq(RpcReq req);
}
