package com.jiawei.wu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcReq implements Serializable {
    private static final long serialVersionUID=1L;

    private String reqId;
    private String interfaceName;
    private String methodName;
    private Object[] params;
    private Class<?>[] paramTypes;
    private String version;
    private String group;
}
