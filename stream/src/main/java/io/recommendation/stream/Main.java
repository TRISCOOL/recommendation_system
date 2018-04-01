package io.recommendation.stream;

import com.google.gson.reflect.TypeToken;
import io.recommendation.stream.function.GetMovieMapScore;
import io.recommendation.stream.function.UpdataStatusForMovie;
import io.recommendation.stream.model.Rank;
import io.recommendation.stream.model.Rating;
import io.recommendation.stream.producer.KafkaSender;
import io.recommendation.stream.utils.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String HDFS_CHECK= "hdfs://192.168.102.3:8020/rank4/";

    public static void main(String[] args){

        //创建流对象
        SparkConf conf = new SparkConf().setAppName("realTimeRank8");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        javaSparkContext.setLogLevel("WARN");

        JavaStreamingContext jsc = new JavaStreamingContext(javaSparkContext, Durations.seconds(10));

        //创建流对象
        Map<String,Object> kafkaConfig = getKafkaParams();
        Collection<String> topics = Arrays.asList("scoreMessage");


        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        jsc,
                        LocationStrategies.PreferBrokers(),
                        ConsumerStrategies.Subscribe(topics,kafkaConfig)
                );

        /*stream.print();*/

        jsc.checkpoint(HDFS_CHECK);

        JavaPairDStream<String,Integer> movieScore = stream.mapToPair(new PairFunction<ConsumerRecord<String, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(ConsumerRecord<String, String> record) throws Exception {
                String value = record.value();
                System.out.println(value);
                Rating rating = JsonUtil.jsonToObject(value,new TypeToken<Rating>(){});
                System.out.println("rating :"+JsonUtil.objectToJson(rating)+"--"+rating.getMovieId()+":"+rating.getRating());
                return new Tuple2<String, Integer>(rating.getMovieId().toString(),rating.getRating());
            }
        });

        movieScore.print();

        JavaPairDStream<String,Integer> movieScoreCount = movieScore.updateStateByKey(new UpdataStatusForMovie());
        movieScoreCount.print();

        movieScoreCount.foreachRDD(new VoidFunction<JavaPairRDD<String, Integer>>() {
            @Override
            public void call(JavaPairRDD<String, Integer> rdd) throws Exception {
                rdd.foreach(new VoidFunction<Tuple2<String, Integer>>() {
                    @Override
                    public void call(Tuple2<String, Integer> tuple2) throws Exception {
                        if (tuple2 != null){
                            KafkaSender kafkaSender = new KafkaSender();
                            String movieId = tuple2._1;
                            Integer score = tuple2._2;
                            Rank rank = new Rank();
                            rank.setMovieId(movieId);
                            rank.setScore(score);
                            kafkaSender.sendMessage(movieId, JsonUtil.objectToJson(rank));
                        }
                    }
                });
            }
        });
        jsc.start();
        try {
            jsc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jsc.close();
    }

    private static Map<String,Object> getKafkaParams(){
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "192.168.101.11:9092,192.168.101.8:9092,192.168.101.5:9092");
        kafkaParams.put("group.id", "realTimeRank3");
        kafkaParams.put("enable.auto.commit", "false");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return kafkaParams;
    }
}
