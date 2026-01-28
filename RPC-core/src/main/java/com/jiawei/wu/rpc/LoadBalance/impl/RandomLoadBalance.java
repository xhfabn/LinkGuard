package com.jiawei.wu.rpc.LoadBalance.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiawei.wu.rpc.LoadBalance.LoadBalance;

import java.util.List;

public class RandomLoadBalance implements LoadBalance {

    @Override
    public String select(List<String> list) {
        return RandomUtil.randomEle(list);
    }
}
