package com.losedream.rabbitmq.consumer;

import com.losedream.rabbitmq.Config;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.losedream.rabbitmq.Config.*;

/**
 * rabbitmq 消费者 demo
 *
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/6 10:06
 */
public class RabbitMQConsumer {

    public static void main(String[] args) throws Exception{

        Address[] addresses = {
                new Address(IP_ADDR, Config.PORT)
        };

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(DEFAULT_USER_PASSWORD);
        factory.setPassword(DEFAULT_USER_PASSWORD);
        // 这里获取连接的方式与生产者有些不同 请注意
        Connection connection = factory.newConnection(addresses);
        // 创建信道
        Channel channel = connection.createChannel();
        // 设置客户端最多接收未被 ack 的消息的个数
        channel.basicQos(64);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("receive message: " + s);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(QUEUE_NAME, consumer);

        // 等待回调函数执行完毕之后 关闭资源
        TimeUnit.SECONDS.sleep(5);

        channel.close();
        connection.close();

    }

}
