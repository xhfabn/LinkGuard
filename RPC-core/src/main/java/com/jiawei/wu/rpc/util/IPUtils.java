package com.jiawei.wu.rpc.util;

import cn.hutool.core.util.StrUtil;

import java.net.InetSocketAddress;
import java.util.Objects;

public class IPUtils {
    public static String toIpPort(InetSocketAddress address) {
        if(Objects.isNull(address)) {
            throw new IllegalArgumentException("address is null");
        }
        String host = address.getHostString();
        if (Objects.equals(host, "localhost")) {
            host = "127.0.0.1";
        }
        return host+ StrUtil.COLON+address.getPort();
    }

    public static InetSocketAddress toInetSocketAddress(String address) {
        if (StrUtil.isBlank(address)) {
            throw new IllegalArgumentException("address不能为空");
        }

        String[] split = address.split(StrUtil.COLON);
        if (split.length != 2) {
            throw new IllegalArgumentException("address格式异常, address: " + address);
        }

        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }
}
