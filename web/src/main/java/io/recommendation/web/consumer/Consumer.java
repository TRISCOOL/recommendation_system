package io.recommendation.web.consumer;

import com.google.gson.reflect.TypeToken;
import io.recommendation.common.bean.Rank;
import io.recommendation.common.service.RankService;
import io.recommendation.common.util.SpringUtils;
import io.recommendation.common.util.Util;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer {

    private RankService rankService = SpringUtils.getBean(RankService.class);

    private KafkaConsumer<String,String> consumer;

    private static ExecutorService executors = Executors.newFixedThreadPool(10);

    private final int minBatchSize = 20;

    public Consumer(String topics, String bootstrapServer){
        if (consumer != null)return;
        synchronized (this){
            if (consumer != null)return;
            Properties properties = getProperties(bootstrapServer);
            consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(Arrays.asList(topics));
        }
    }

    public void run(){
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            int pollSize = records.count();
            int i = 0;
            for (ConsumerRecord<String,String> record : records){
                System.out.println(record.value());
                executors.execute(()->{

                    Rank rank = getRank(record.value());

                    rankService.insertOrUpdate(rank);
                });
                i++;
            }
            if(i>0&&(i==pollSize||i>=minBatchSize)){
                consumer.commitSync();//手动提交offset
            }

        }
    }

    public Rank getRank(String json){
        if (json == null)
            return null;

        return Util.jsonToObject(json,new TypeToken<Rank>(){});
    }

    @SuppressWarnings("Duplicates")
    public Properties getProperties(String bootstrapServer){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServer);
        props.put("group.id", "rankConsumer");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }
}
