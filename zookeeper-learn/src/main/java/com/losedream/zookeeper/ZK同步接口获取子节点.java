package com.losedream.zookeeper;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 17:31
 */
public class ZK同步接口获取子节点 implements Watcher {


    private static Logger logger = LoggerFactory.getLogger(ZK同步接口获取子节点.class);
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception{

        String path = "/zk-book";
        zooKeeper = LinkZK.getZooKeeper(new ZK同步接口获取子节点());
        countDownLatch.await();

        zooKeeper.create(path, StringUtils.EMPTY.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(path + "/c1", StringUtils.EMPTY.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        List<String> children = zooKeeper.getChildren(path, true);
        System.out.println(children);

        zooKeeper.create(path + "/c2", StringUtils.EMPTY.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected.equals(event.getState())) {
            if (Event.EventType.None.equals(event.getType()) && null == event.getPath()) {
                countDownLatch.countDown();
            } else if (Event.EventType.NodeChildrenChanged.equals(event.getType())) {
                try {
                    logger.error("重新获取子节点: " + zooKeeper.getChildren(event.getPath(), true));
                } catch (Exception e) {}
            }
        }
    }
}
