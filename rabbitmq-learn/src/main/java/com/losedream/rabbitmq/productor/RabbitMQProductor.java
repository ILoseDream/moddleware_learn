package com.losedream.rabbitmq.productor;

import com.rabbitmq.client.*;

import static com.losedream.rabbitmq.Config.*;
import static com.rabbitmq.client.BuiltinExchangeType.DIRECT;

/**
 * rabbitmq 消息生产者 demo
 *
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/6 9:50
 */
public class RabbitMQProductor {

    private static final String USER = DEFAULT_USER_PASSWORD;

    private static final String PASSWORD = DEFAULT_USER_PASSWORD;

    public static void main(String[] args) throws Exception {

        // 创建并配置一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDR);
        factory.setPort(PORT);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        // 创建一个 type="direct"、持久化、非自动删除的交换机
        AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(EXCHANGE_NAME, DIRECT, true, false, null);

        // 创建一个持久化、非排他的、非自动删除的队列
        AMQP.Queue.DeclareOk declareOk1 = channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 交换机与队列通过路由键进行绑定
        AMQP.Queue.BindOk bindOk = channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        // 发送一条持久化的消息 : "hello world"
        String message = "hello world";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.TEXT_PLAIN, message.getBytes());

        channel.queueDeclare("normalQueue", true, false, false, null);


        // 关闭资源
        channel.close();
        connection.close();

    }


}









