package com.jiawei.wu;

import lombok.SneakyThrows;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
public class AppTest {

    @SneakyThrows
    public static void main(String[] args) {
        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // 要连接的服务器列表
                .connectString("192.168.79.130:2181")
                .retryPolicy(retryPolicy)
                .build();

        zkClient.start();

        String path = "/node1";

        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, path, true);

        // 给某个节点注册子节点监听器
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_REMOVED) {
                System.out.println("子节点删除");
            } else if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                System.out.println("子节点新增");
            } else if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_UPDATED) {
                System.out.println("子节点修改");
            }
        };

        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        // 启动pathChildrenCache
        pathChildrenCache.start();

        // 模拟程序运行一段时间
        System.in.read();
    }
}
