package com.losedream.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 16:36
 */
public class ZooKeeper_Constructor_Usage_Simple implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{

        ZooKeeper zooKeeper = new ZooKeeper(Config.URL, 5000,
                new ZooKeeper_Constructor_Usage_Simple());

        System.out.println(zooKeeper.getState());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("zookeeper session established.");

    }

    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: " + event);
        if (Event.KeeperState.SyncConnected.equals(event.getState())) {
            countDownLatch.countDown();
        }
    }
}



















