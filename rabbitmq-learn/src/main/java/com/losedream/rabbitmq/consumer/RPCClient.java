package com.losedream.rabbitmq.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/10 21:40
 */
public class RPCClient {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
    private QueueingConsumer consumer;

    public RPCClient() throws IOException {
        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public String call(String message) throws Exception{
        String response = null;
        String corrId = UUID.randomUUID().toString () ;

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId)
                .replyTo(replyQueueName).build();
        channel.basicPublish ("", requestQueueName , props, message . getBytes());
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().endsWith(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }
        return response;
    }

    public void close() throws IOException {
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        RPCClient rpcClient = new RPCClient();
        System . out . println ("[ x] Requesting fib(30 )");

        String response = rpcClient.call("30");
        System.out . println ("[.] Got '" + response + "'");
        rpcClient.close();
    }

}
