package com.github.black.hole.message.producer.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author hairen.long
 * @date 2021/10/5
 */
public class KafkaProducerMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        producer();
    }

    public static void producer() throws ExecutionException, InterruptedException {
        Properties pro = new Properties();
        pro.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "ifarmshop.com:9092");
        //kafka 是一个持久化数据的MQ，以byte[]方式存储，数据需要转换为byte[],kafka不会对数据做处理，生产和消费方需要约定编码
        //kafka中的零拷贝 使用sendfile系统调用，实现数据快速消费

        pro.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        pro.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(pro);


        /*
            topic : test007
            partition数量：1
            模拟三种商品，每种商品有线性的3个ID
            相同的商品最好去到一个分区里
         */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ProducerRecord<String, String> record = new ProducerRecord<>("test007", "item-" + j, "value-" + i);
                Future<RecordMetadata> send = producer.send(record);

                RecordMetadata rm = send.get();
                int partition = rm.partition();
                long offset = rm.offset();
                System.out.println("key:" + record.key() + ", value:" +
                        record.value() + ", partition:" + partition + ", offset:" + offset);
            }
        }

    }

    public void test(){
        // 1.配置生产者的属性，各个属性的作用可以在这里看：http://kafka.apachecn.org/documentation.html#producerconfigs
        Properties props = new Properties();
        props.put("bootstrap.servers", "ifarmshop.com:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 2.实例化一个生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            // 3.实例化一条消息，第一个参数是主题的名称，第二个参数是消息的内容
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("test01", Integer.toString(i));
            // 4.发送消息
            producer.send(
                    record,
                    new Callback() {
                        // 5.在回调函数里处理结果，这样就不会阻塞了
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            System.out.println(
                                    "topic: "
                                            + metadata.topic()
                                            + ", partition: "
                                            + metadata.partition()
                                            + ", offset: "
                                            + metadata.offset());
                        }
                    });
        }
        // 6.关闭资源
        producer.close();
    }

}
