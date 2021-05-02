package com.losedream.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

import static com.losedream.rocketmq.constant.Constants.*;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/5/2 9:37
 */
public class SyncProducer {

    /**
     * 发送消息大小
     */
    private static final int MESSAGE_SIZE = 100;

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException,
            RemotingException, InterruptedException, MQBrokerException {

        // 构建消息发送者
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(NAMESRV_ADDR);
        producer.start();
        producer.setSendMsgTimeout(10000);

        // 构建消息
        for (int i = 0; i < MESSAGE_SIZE; i++) {

            byte[] messageBody = ("Hello Rocketmq " + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
            Message message = new Message(TOPIC, TAG, messageBody);

            // 同步发送
            SendResult sendResult = producer.send(message);
            System.err.printf("%s%n", sendResult);
        }

        // 关闭
        producer.shutdown();
    }


}
