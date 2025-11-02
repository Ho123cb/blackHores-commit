package com.heima.kafakademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@SpringBootTest
class KafakaDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 用于作为kafaka生产者发送数据
     */
    @Test
    void test(){
            //1.kafka的配置信息
            Properties properties = new Properties();
            //kafka的连接地址
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.130:9092");
            //发送失败，失败的重试次数
            properties.put(ProducerConfig.RETRIES_CONFIG,5);
            //消息key的序列化器
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
            //消息value的序列化器
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

            //2.生产者对象
            KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

            //封装发送的消息
            ProducerRecord<String,String> record = new ProducerRecord<String, String>("itcast-topic-input","100001","hello kafka");

            //3.发送消息
//            producer.send(record);
            for(int i = 0; i < 5; i++)
                producer.send(record);

            //4.关闭消息通道，必须关闭，否则消息发送不成功
            producer.close();


    }

}
