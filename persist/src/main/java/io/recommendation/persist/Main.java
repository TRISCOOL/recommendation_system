package io.recommendation.persist;

import io.recommendation.persist.consumer.Consumer;
import io.recommendation.persist.hook.PersistWithHbaseHook;
import io.recommendation.persist.hook.StreamHook;
import io.recommendation.persist.service.HbaseService;
import io.recommendation.persist.service.HbaseServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Consumer consumer = new Consumer("scoreMessage","192.168.101.11:9092");
        List<StreamHook> streamHooks = new ArrayList<StreamHook>();
        streamHooks.add(new PersistWithHbaseHook());
        consumer.run(streamHooks);
    }

/*    public void test(){
        HbaseService hbaseService = new HbaseServiceImpl("192.168.102.3:2181,192.168.102.13:2181,192.168.102.24:2181");
        try {
            hbaseService.putdata("reconmmend_relationship","0","movie_favor","2","4");

            String value = hbaseService.getValue("reconmmend_relationship","0","movie_favor","2");

            System.out.println(value);
        } catch (IOException e) {
            hbaseService.close();
            e.printStackTrace();
        }
    }*/
}
