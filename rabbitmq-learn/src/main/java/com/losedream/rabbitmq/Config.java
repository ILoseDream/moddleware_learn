package com.losedream.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/6 10:06
 */
public class Config {

    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "exchange_demo";

    /**
     * 路由键
     */
    public static final String ROUTING_KEY = "routing_key_demo";

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "queue_demo";

    /**
     * 远程ip
     */
    public static final String IP_ADDR = "8.136.188.16";

    /**
     * 尝试ip
     */
    public static final String MAY_BE_IP = "172.24.185.165";

    /**
     * 端口号
     */
    public static final int PORT = ConnectionFactory.DEFAULT_AMQP_PORT;

    /**
     * 默认的用户及密码
     */
    public static final String DEFAULT_USER_PASSWORD = "root";

}
