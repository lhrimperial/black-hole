package com.github.black.hole.message.producer.rabbitmq.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @author hairen.long
 * @date 2020/11/12
 */
public class AsyncConfirmProducer {
    private static final String EXCHANGE_NAME = "async-confirm-exchange";

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
        long msgCount = 1;
        SortedSet<Long> confirmSet = new TreeSet<Long>();
        channel.confirmSelect();
        channel.addConfirmListener(
                new ConfirmListener() {
                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println("Ack,SeqNo：" + deliveryTag + ",multiple：" + multiple);
                        if (multiple) {
                            confirmSet.headSet(deliveryTag - 1).clear();
                        } else {
                            confirmSet.remove(deliveryTag);
                        }
                    }

                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println("Nack,SeqNo：" + deliveryTag + ",multiple：" + multiple);
                        if (multiple) {
                            confirmSet.headSet(deliveryTag - 1).clear();
                        } else {
                            confirmSet.remove(deliveryTag);
                        }
                        // 注意这里需要添加处理消息重发的场景
                    }
                });
        // 演示发送100个消息
        while (msgCount <= batchCount) {
            long nextSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish(EXCHANGE_NAME, "", null, "async confirm test".getBytes());
            confirmSet.add(nextSeqNo);
            msgCount = nextSeqNo;
        }

        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
