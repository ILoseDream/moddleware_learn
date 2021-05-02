package com.losedream.rabbitmq.productor;

import com.losedream.rabbitmq.Config;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.losedream.rabbitmq.Config.*;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/10 21:31
 */
public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建并配置一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDR);
        factory.setPort(PORT);
        factory.setUsername(Config.DEFAULT_USER_PASSWORD);
        factory.setPassword(Config.DEFAULT_USER_PASSWORD);

        // 创建连接
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        System.out.println("[x] Awaiting RPC requests");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                AMQP.BasicProperties replayProps = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
                String response = "";
                try {

                    String message = new String(body, "UTF-8");
                    int i = Integer.parseInt(message);
                    System.out.println(" [.] fib(" + message + ")");
                    response += fib(i);
                } catch (Exception e) {
                    System.out.println ("[.] " + e.toString ()) ;
                }finally {
                    channel.basicPublish("", properties.getReplyTo(), replayProps, response.getBytes("UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

    }

    private static Integer fib(int i) {
        if (i == 0 || i == 1) {
            return i;
        }

        return fib(i - 1) + fib(i - 2);

    }


}
