package io.recommendation.persist.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
    private static Gson gson = new Gson();

    public static String objectToJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T jsonToObject(String json, TypeToken<T> tTypeToken){
        return gson.fromJson(json,tTypeToken.getType());
    }
}
