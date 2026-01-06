package com.jiawei.wu.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcServiceConfig {
    private String version = "";
    private String group = "";
    private Object service;

    public RpcServiceConfig(Object service) {
        this.service = service;
    }
    public List<String> rpcServiceNames() {
        return interfaceNames().stream()
            .map(interfaceName -> interfaceName + getVersion() + getGroup())
            .collect(Collectors.toList());
    }

    private List<String> interfaceNames() {
        return List.of(service.getClass().getInterfaces()).stream()
            .map(Class::getCanonicalName)
            .collect(Collectors.toList());
    }
}
