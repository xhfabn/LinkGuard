package com.jiawei.wu.server.service;

import com.jiawei.wu.api.NativeService;

public class NativeServiceImpl implements NativeService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
