package io.recommendation.stream;

import io.recommendation.stream.kafka_stream.RankStreamWithKafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.KafkaUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        //创建流对象
        SparkConf conf = new SparkConf().setAppName("realTimeRank");
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(1));

        RankStreamWithKafka rankStreamWithKafka = new RankStreamWithKafka(jsc);
        rankStreamWithKafka.run();

        jsc.start();
        try {
            jsc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jsc.close();
    }

}
