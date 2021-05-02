package com.losedream.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 16:58
 */
public class ZooKeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {

    private static CountDownLatch downLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{

        ZooKeeper zooKeeper = new ZooKeeper(Config.URL, 5000,
                new ZooKeeper_Constructor_Usage_With_SID_PASSWD()
        );
        downLatch.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();
        // use illegal sessionId and session password
        zooKeeper = new ZooKeeper(Config.URL, 5000,
                new ZooKeeper_Constructor_Usage_With_SID_PASSWD(),
                sessionId, "test".getBytes());

        // use sessionId and session password
        zooKeeper = new ZooKeeper(Config.URL, 5000,
                new ZooKeeper_Constructor_Usage_With_SID_PASSWD(), sessionId, sessionPasswd);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: " + event);
        if (Event.KeeperState.SyncConnected.equals(event.getState())) {
            downLatch.countDown();
        }
    }
}
