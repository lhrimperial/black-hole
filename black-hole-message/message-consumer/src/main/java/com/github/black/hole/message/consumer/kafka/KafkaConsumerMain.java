package com.github.black.hole.message.consumer.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author hairen.long
 * @date 2021/10/5
 */
public class KafkaConsumerMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        cousumer();
    }

    public static void cousumer() {
        Properties pro = new Properties();
        pro.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ifarmshop.com:9092");
        //kafka 是一个持久化数据的MQ，以byte[]方式存储，数据需要转换为byte[],kafka不会对数据做处理，生产和消费方需要约定编码
        //kafka中的零拷贝 使用sendfile系统调用，实现数据快速消费

        pro.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        pro.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //设置消费分组
        pro.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "TIGER_007");

        /**
         * What to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server (e.g. because that data has been deleted):
         * <ul>
         *      <li>earliest: automatically reset the offset to the earliest offset</li>
         *      <li>latest: automatically reset the offset to the latest offset</li>
         *      <li>none: throw exception to the consumer if no previous offset is found for the consumer's group</li>
         *     anything else: throw exception to the consumer.
         * </li></ul>";
         *
         */
        pro.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //offset 自动提交
        pro.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        //pro.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "");//5秒
        //pro.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "");//poll 拉取多少条数据

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(pro);

        consumer.subscribe(Arrays.asList("test007"), new ConsumerRebalanceListener() {
            /**
             * 消费组和对应的 partition 的rebalance
             * 例如 刚开始只有一个消费者对应两个 partition
             * 这时同一消费组中新增一个消费者，这时消费者和 partition的对应关系就会变成一个消费者对应一个partition
             */
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("---onPartitionsRevoked:");
                Iterator<TopicPartition> iter = partitions.iterator();
                while(iter.hasNext()){
                    System.out.println(iter.next().partition());
                }
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("---onPartitionsAssigned:");
                Iterator<TopicPartition> iter = partitions.iterator();
                while (iter.hasNext()) {
                    System.out.println(iter.next().partition());
                }
            }
        });

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(0));

            if (!records.isEmpty()) {
                Iterator<ConsumerRecord<String, String>> iter = records.iterator();

                System.out.println("================="+records.count()+"==================");

                //==================================================================================================================//
                //自动提交 ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true"
                /*while (iter.hasNext()) {
                    ConsumerRecord<String, String> record = iter.next();
                    int partition = record.partition();
                    long offset = record.offset();

                    System.out.println("key: " + record.key() + " value:" + record.value() + " partition:" + partition + " offset:" + offset);
                }*/
                //==================================================================================================================//

                Set<TopicPartition> partitions = records.partitions(); //每次poll的时候是取多个分区的数据
                //且每个分区内的数据是有序的

                /**
                 * 如果手动提交offset  即 ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"
                 * 可以按一下三种方式处理
                 * 1、按消息进度同步提交
                 * 2、按分区粒度同步提交
                 * 3、按当前poll的批次同步提交
                 *
                 * 思考：如果在多个线程下
                 * 1、以上1，3的方式不用多线程
                 * 2、以上2的方式最容易想到多线程方式处理，即每个分区用一个线程处理
                 */

                for (TopicPartition partition : partitions) {
                    List<ConsumerRecord<String, String>> pRecords = records.records(partition);
                    //在一个批次里，按分区获取poll回来的数据
                    //线性按分区处理，还可以并行按分区处理用多线程的方式
                    Iterator<ConsumerRecord<String, String>> piter = pRecords.iterator();
                    while(piter.hasNext()){
                        ConsumerRecord<String, String> next = piter.next();
                        int par = next.partition();
                        long offset = next.offset();
                        String key = next.key();
                        String value = next.value();
                        long timestamp = next.timestamp();


                        System.out.println("key: "+ key+" val: "+ value+ " partition: "+par + " offset: "+ offset+"time:: "+ timestamp);

                        TopicPartition sp = new TopicPartition("msb-items", par);
                        OffsetAndMetadata om = new OffsetAndMetadata(offset);
                        HashMap<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                        map.put(sp,om);

                        //这个是最安全的，每条记录级的更新，对应第一点，按消息进度同步提交
                        consumer.commitSync(map);
                        //单线程，多线程，都可以
                    }
                    long poff = pRecords.get(pRecords.size() - 1).offset();//获取分区内最后一条消息的offset

                    OffsetAndMetadata pom = new OffsetAndMetadata(poff);
                    HashMap<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                    map.put(partition,pom);
                    //这个是第二种，分区粒度提交offset
                    consumer.commitSync( map );
                    /**
                     * 因为你都分区了
                     * 拿到了分区的数据集
                     * 期望的是先对数据整体加工
                     */
                }
                //这个就是按poll的批次提交offset，第3点
                consumer.commitSync();
            }
        }


    }

    public static void kafkaListTopics() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        // 只需要提供一个或多个 broker 的 IP 和端口
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ifarmshop.com:9092");
        // 创建 AdminClient 对象
        AdminClient client = KafkaAdminClient.create(props);
        // 获取 topic 列表
        Set topics = client.listTopics().names().get();
        System.out.println(topics);
    }


    public static void zookeeperListTopics() throws IOException, KeeperException, InterruptedException {
        // 创建 ZooKeeper 对象，三个参数分别是 ZK 的 URL、超时时间和一个 watcher 对象，在这里将 watcher 对象设为空即可
        ZooKeeper zk = new ZooKeeper("ifarmshop.com:21811", 10000, null);
        // 查看 /brokers/topics 的子节点
        List<String> topics = zk.getChildren("/brokers/topics", false);
        System.out.println(topics);
    }

}
