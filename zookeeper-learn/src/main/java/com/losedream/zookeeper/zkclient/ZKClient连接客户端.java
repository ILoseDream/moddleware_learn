package com.losedream.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/11 18:08
 */
public class ZKClient连接客户端 {

    public ZkClient linkZK() {
        return new ZkClient(Config.URL, 5000);
    }

}
