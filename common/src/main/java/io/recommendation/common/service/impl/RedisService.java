package io.recommendation.common.service.impl;


import com.google.gson.reflect.TypeToken;
import io.recommendation.common.util.Util;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/4.
 */
@Component
public class RedisService {

    @Resource
    private JedisPool pool;


    public void setStr(String key, String val, final long timeout) {
        try(Jedis jedis = pool.getResource()) {

            jedis.set(key, val);
            if (timeout == 0l) {
                setStr(key, val);
            } else {
                jedis.expire(key, (int)timeout);
            }

        }

    }

    public Map<String,String> hgetAll(String key){
        try(Jedis jedis = pool.getResource()){
            return jedis.hgetAll(key);
        }
    }


    public void setStr(String key, String val) {
        try(Jedis jedis = pool.getResource()) {
            jedis.set(key, val);
        }
    }

    public String getStr(String key) {
        try(Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    public boolean exists(String key) {
        try(Jedis jedis = pool.getResource()) {
            return jedis.exists(key);
        }
    }

    public Map<String,String> hget(String key){
        String result = getStr(key);
        Map<String,String> value = Util.jsonToObject(result,new TypeToken<Map<String,String>>(){});
        return value;
    }

    public String hget(String key,String field){
        try(Jedis jedis = pool.getResource()) {
            return jedis.hget(key,field);
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        try(Jedis jedis = pool.getResource()){
            return jedis.hmset(key, hash);
        }
    }

    public Long incr(String key){
        try(Jedis jedis = pool.getResource()){
            return jedis.incr(key);
        }
    }

    public void expire(String key,int seconds){
        try(Jedis jedis = pool.getResource()) {
            jedis.expire(key,seconds);
        }
    }

    public void del(String key){
        try(Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

}
