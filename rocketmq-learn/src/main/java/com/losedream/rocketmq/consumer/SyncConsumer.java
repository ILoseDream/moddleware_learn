package com.losedream.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static com.losedream.rocketmq.constant.Constants.*;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/5/2 9:54
 */
public class SyncConsumer {

    public static void main(String[] args) throws MQClientException {

        // 初始化 DefaultMQPushConsumer 实例 并设置属性
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PRODUCER_GROUP);
        consumer.setNamesrvAddr(NAMESRV_ADDR);

        // 指定消费起始点
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 订阅一个 topic 方便消费
        consumer.subscribe(TOPIC, "*");

        // 注册回调  当接受到消息的时候执行
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.err.printf(Thread.currentThread().getName() + " receive new message " + JSON.toJSONString(list) + "%n");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 启动消费者实例
        consumer.start();

    }

}
