package io.recommendation.stream.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaSender {

    private String BOOTSTRAP_SERVERS = "192.168.101.11:9092,192.168.101.8:9092,192.168.101.5:9092";
    private static final String topic = "rank";

    private Producer<String, String> producer;


    public KafkaSender(){
        if (producer != null) return;
        synchronized (this){
            if (producer != null) return;
            Properties properties = getConfig();
            producer = new KafkaProducer<String, String>(properties);
        }
    }

    public void sendMessage(String key,String message){
        producer.send(new ProducerRecord<String, String>(topic,key,message));
    }

    public void closeProducer(){
        producer.close();
    }


    @SuppressWarnings("Duplicates")
    private Properties getConfig(){
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }
}
