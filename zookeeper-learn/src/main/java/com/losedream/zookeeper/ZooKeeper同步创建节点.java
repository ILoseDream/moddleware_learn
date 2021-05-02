package com.losedream.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 17:10
 */
public class ZooKeeper同步创建节点 implements Watcher {

    private static Logger logger = LoggerFactory.getLogger(ZooKeeper同步创建节点.class);

    private static CountDownLatch downLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{

        ZooKeeper zooKeeper = new ZooKeeper(Config.URL, 5000, new ZooKeeper同步创建节点());

        downLatch.await();

        String path1 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        logger.error("success create znode: " + path1);

        String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        logger.error("success create znode: " + path2);

    }


    public void process(WatchedEvent event) {
        logger.error("Receive watched event: " + event);
        if (Event.KeeperState.SyncConnected.equals(event.getState())) {
            downLatch.countDown();
        }
    }

}
