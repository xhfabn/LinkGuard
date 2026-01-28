package com.jiawei.wu.rpc.factory;

import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonFactory {
    private static final Map<Class<?>,Object> INSTANCE_CACHE=new ConcurrentHashMap<>();

    private SingletonFactory(){}

    @SneakyThrows
    public static <T> T getInstance(Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            throw new IllegalArgumentException("Class must not be null");
        }
        if (INSTANCE_CACHE.containsKey(clazz)) {
            return clazz.cast(INSTANCE_CACHE.get(clazz));
        }
        synchronized (SingletonFactory.class) {
            if (INSTANCE_CACHE.containsKey(clazz)) {
                return clazz.cast(INSTANCE_CACHE.get(clazz));
            }
            T t=clazz.getConstructor().newInstance();
            INSTANCE_CACHE.put(clazz,t);
            return t;
        }

    }

}
