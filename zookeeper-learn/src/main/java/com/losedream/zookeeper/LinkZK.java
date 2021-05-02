package com.losedream.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 17:32
 */
public class LinkZK {

    public static ZooKeeper getZooKeeper(Watcher watcher) throws Exception{
        return new ZooKeeper(Config.URL, 5000, watcher);
    }

}
