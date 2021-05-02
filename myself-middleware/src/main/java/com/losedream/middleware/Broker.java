package com.losedream.middleware;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: losedream ... (｡￫‿￩｡)
 * @since: 2021/2/17 15:53
 */
public class Broker {

    // 队列存储消息的最大数量
    private static final int MAX_SIZE = 3;

    // 保存消息数据的容器
    private static BlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);

    // 生产消息
    public static void produce(String msg) {
        if (messageQueue.offer(msg)) {
            System.out.println("成功想消息处理中心投递消息: " + msg + ", 当前暂存的消息数量是: " + messageQueue.size());
        } else {
            System.out.println("消息处理中心内暂存的消息达到最大负荷， 不能继续存放消息");
        }
        System.out.println("==========================================================");
    }

    // 消费消息
    public static String consumer() {
        String msg = messageQueue.poll();
        if (msg != null) {
            System.out.println("已经消费的消息: " + msg + ", 当前暂存的消息数量是: " + messageQueue.size());
        } else {
            System.out.println("消费处理中心内没有消息可供消费");
        }
        System.out.println("==========================================================");
        return msg;
    }


}
