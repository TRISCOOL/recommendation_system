package io.recommendation.stream.kafka_stream;

import io.recommendation.stream.function.GetMovieMapScore;
import io.recommendation.stream.function.UpdataStatusForMovie;
import io.recommendation.stream.model.Rank;
import io.recommendation.stream.producer.KafkaSender;
import io.recommendation.stream.utils.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RankStreamWithKafka {

    private static final String JDBC_URL= "jdbc:mysql://192.168.102.24/recommendation?useUnicode=true&characterEncoding=UTF-8";
    private JavaStreamingContext javaStreamingContext;

    public RankStreamWithKafka(JavaStreamingContext javaStreamingContext){
        this.javaStreamingContext = javaStreamingContext;
    }

    public void run(){
        //创建流对象
        Map<String,Object> kafkaConfig = getKafkaParams();
        Collection<String> topics = Arrays.asList("scoreMessage");


        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        this.javaStreamingContext,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topics, kafkaConfig)
                );

        JavaPairDStream<String,Integer> movieScore = stream.mapToPair(new GetMovieMapScore());

        JavaPairDStream<String,Integer> movieScoreCount = movieScore.updateStateByKey(new UpdataStatusForMovie());


        movieScoreCount.foreachRDD(rdd -> {
            rdd.foreachPartition(partitionRecords -> {
                KafkaSender kafkaSender = new KafkaSender();
                while (partitionRecords.hasNext()){
                    String movieId = partitionRecords.next()._1;
                    Integer score = partitionRecords.next()._2;
                    Rank rank = new Rank();
                    rank.setMovieId(movieId);
                    rank.setScore(score);
                    kafkaSender.sendMessage(movieId, JsonUtil.objectToJson(rank));
                }
            });
        });

    }


    private Map<String,Object> getKafkaParams(){
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "192.168.101.11:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "realTimeRank");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        return getKafkaParams();
    }
}
