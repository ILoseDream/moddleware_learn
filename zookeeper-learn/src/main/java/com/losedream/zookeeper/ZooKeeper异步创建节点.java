package com.losedream.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 17:20
 */
public class ZooKeeper异步创建节点 implements Watcher {

    private static Logger logger = LoggerFactory.getLogger(ZooKeeper异步创建节点.class);

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{

        ZooKeeper zooKeeper = new ZooKeeper(Config.URL, 5000, new ZooKeeper异步创建节点());

        countDownLatch.await();

        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "I am Context");

        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "I am Context");

        zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am Context");

        Thread.sleep(Integer.MAX_VALUE);

    }

    public void process(WatchedEvent event) {
        logger.error("接收到的监听器的事件是: " + event);
        if (Event.KeeperState.SyncConnected.equals(event.getState())) {
            countDownLatch.countDown();
        }
    }

    static class IStringCallback implements AsyncCallback.StringCallback {
        public void processResult(int rc, String path, Object ctx, String name) {
            logger.error("创建的结果是: [" + rc + ", " + path + ", " + ctx + ", 实际的路径是: " + name);
        }
    }


}
