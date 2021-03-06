package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer {
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
                executors.execute(()->{
                    try {
                        System.out.println(record.value());
                        Process process = Runtime.getRuntime().exec(record.value());
                        process.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                i++;
            }
            if(i>0&&(i==pollSize||i>=minBatchSize)){
                consumer.commitSync();//手动提交offset
            }

        }
    }

    public Properties getProperties(String bootstrapServer){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServer);
        props.put("group.id", "commandExecutor");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }
}
