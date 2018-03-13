package io.recommendation.stream.function;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function2;

import java.util.List;

public class UpdataStatusForMovie implements Function2<List<Integer>,Optional<Integer>,Optional<Integer>>{
    @Override
    public Optional<Integer> call(List<Integer> values, Optional<Integer> status) throws Exception {
        Integer nowScore = 0;

        if (status.isPresent()){
            nowScore = status.get();
        }

        for (Integer everyScore : values){
            nowScore += everyScore;
        }

        //返回更新后的值
        return Optional.of(nowScore);
    }
}
