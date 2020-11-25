package com.github.black.hole.message.consumer.rabbitmq;

import com.github.black.hole.message.api.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author hairen.long
 * @date 2020/11/13
 */
public class Worker {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicQos(1);

        final DefaultConsumer consumer =
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(
                            String consumerTag,
                            Envelope envelope,
                            AMQP.BasicProperties properties,
                            byte[] body)
                            throws IOException {
                        String message = new String(body, "UTF-8");
                        System.out.println(" [x] Received '" + message + "'");
                        try {
                            try {
                                Thread.sleep(5000); // 执行耗时操作
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } finally {
                            System.out.println(" [x] Done" + message);
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }
                    }
                };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }
}
