package io.recommendation.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.UUID;

public class Util {

    private static Gson gson = new Gson();

    public static String objectToJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T jsonToObject(String json,TypeToken<T> tTypeToken){
        return gson.fromJson(json,tTypeToken.getType());
    }

    public static String getUuid(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }


}
