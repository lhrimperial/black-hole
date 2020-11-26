package com.github.black.hole.message.producer.rabbitmq.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @author hairen.long
 * @date 2020/11/12
 */
public class BatchConfirmProducer {

    private static final String EXCHANGE_NAME = "batch-confirm-exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("ifarmshop.com");
        factory.setPort(5673);
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 创建一个Exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        int batchCount = 100;
        int msgCount = 0;
        BlockingQueue blockingQueue = new ArrayBlockingQueue(100);
        try {
            channel.confirmSelect();
            while (msgCount <= batchCount) {
                String message = "batch confirm test";
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                // 将发送出去的消息存入缓存中，缓存可以是一个ArrayList或者BlockingQueue之类的
                blockingQueue.add(message);
                if (++msgCount >= batchCount) {
                    try {
                        if (channel.waitForConfirms()) {
                            // 将缓存中的消息清空
                            blockingQueue.clear();
                        } else {
                            // 将缓存中的消息重新发送
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 将缓存中的消息重新发送
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
