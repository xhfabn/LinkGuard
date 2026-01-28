package com.jiawei.wu.rpc.registry.ZK;

import cn.hutool.core.util.StrUtil;
import com.jiawei.wu.rpc.constant.RpcConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZkClient {
    private static final int BASE_SLEEP_TIME= 1000;
    private static final int MAX_RETRIES=3;
    private CuratorFramework zkClient;
    
    public ZkClient(){
        this(RpcConstants.ZK_IP, RpcConstants.ZK_PORT);
    }
    public ZkClient(String hostname,int port){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);

        this.zkClient = CuratorFrameworkFactory.builder()
                .connectString(hostname+":"+port)
                .retryPolicy(retryPolicy)
                .build();

        log.info("ZkClient connecting to {}:{}",hostname,port);
        this.zkClient.start();

        // 阻塞等待连接建立完成，超时时间10秒
        try {
            this.zkClient.blockUntilConnected(10, TimeUnit.SECONDS);
            log.info("ZkClient connected successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("等待ZooKeeper连接时被中断", e);
        } catch (Exception e) {
            throw new RuntimeException("无法连接到ZooKeeper服务器: " + hostname + ":" + port, e);
        }

        // 添加JVM关闭钩子，确保优雅关闭
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("正在关闭ZooKeeper连接...");
            close();
        }));
    }
    @SneakyThrows
    public void createPersistentNode(String path){
        if(StrUtil.isBlank(path)){
            throw new IllegalArgumentException("path must not be null");
        }
        if(zkClient.checkExists().forPath(path)!=null){
            log.info("节点{}已存在",path);
            return;
        }
        log.info("创建持久化节点:{}",path);
        zkClient.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path);
    }
    @SneakyThrows
    public List<String> getChildrenNodes(String path){
        if(StrUtil.isBlank(path)){
            throw new IllegalArgumentException("path为空");
        }
        return zkClient.getChildren().forPath(path);
    }
    public void close(){
        if(zkClient!=null){
            this.zkClient.close();
        }
    }
}
