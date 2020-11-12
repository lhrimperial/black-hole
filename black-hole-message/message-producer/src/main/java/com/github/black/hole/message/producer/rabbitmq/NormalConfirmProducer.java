package com.github.black.hole.message.producer.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hairen.long
 * @date 2020/11/12
 */
public class NormalConfirmProducer {
    private static final String EXCHANGE_NAME = "normal-confirm-exchange";

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

        try {
            channel.confirmSelect();
            // 发送消息
            String message = "normal confirm test";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            if (channel.waitForConfirms()) {
                System.out.println("send message success");
            } else {
                System.out.println("send message failed");
                // do something else...
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
