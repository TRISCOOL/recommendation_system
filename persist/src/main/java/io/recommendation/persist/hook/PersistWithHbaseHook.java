package io.recommendation.persist.hook;

import com.google.gson.reflect.TypeToken;
import io.recommendation.persist.model.MovieFavor;
import io.recommendation.persist.service.HbaseService;
import io.recommendation.persist.service.HbaseServiceImpl;
import io.recommendation.persist.utils.JsonUtil;

import java.io.IOException;

public class PersistWithHbaseHook implements StreamHook{

    private String ZOOKEEPER_CLUSTER = "192.168.102.3:2181,192.168.102.13:2181,192.168.102.24:2181";

    private String TABLE_NAME = "reconmmend_relationship";
    private String COLUMS_FAMILY = "movie_favor";

    private HbaseService hbaseService;

    @Override
    public void process(String value) {
        init();
        if (value == null)return;

        MovieFavor movieFavor = JsonUtil.jsonToObject(value,new TypeToken<MovieFavor>(){});
        String rowKey = movieFavor.getUserId().toString();
        String columns = movieFavor.getMovieId().toString();
        Integer nowScore = movieFavor.getScore();

        Integer lastScore = getValueByUserAndMovie(rowKey,columns);
        if (lastScore != null){
            nowScore = nowScore + lastScore;
        }

        try {
            hbaseService.putdata(TABLE_NAME,rowKey,COLUMS_FAMILY,columns,nowScore.toString());
        } catch (IOException e) {
            e.printStackTrace();
            hbaseService.close();
        }


    }

    private void init(){
        if (hbaseService != null)return;
        synchronized (this){
            if (hbaseService != null)return;
            hbaseService = new HbaseServiceImpl(ZOOKEEPER_CLUSTER);
        }
    }

    private Integer getValueByUserAndMovie(String rowkey,String columns){
        try {
            String value = hbaseService.getValue(TABLE_NAME,rowkey,COLUMS_FAMILY,columns);
            if (value != null)return Integer.parseInt(value);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            hbaseService.close();
            return null;
        }

    }
}
