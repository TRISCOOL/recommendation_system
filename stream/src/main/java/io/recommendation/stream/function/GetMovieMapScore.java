package io.recommendation.stream.function;

import com.google.gson.reflect.TypeToken;
import io.recommendation.stream.model.Rating;
import io.recommendation.stream.utils.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class GetMovieMapScore implements PairFunction<ConsumerRecord<String,String>,String,Integer> {

    @Override
    public Tuple2<String, Integer> call(ConsumerRecord<String, String> record) throws Exception {
        String jsonValue = record.value();
        Rating rating = JsonUtil.jsonToObject(jsonValue,new TypeToken<Rating>(){});
        return new Tuple2<String, Integer>(rating.getMovieId().toString(),rating.getRating());
    }
}
