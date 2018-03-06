package io.recommendation.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Util {

    private static Gson gson = new Gson();

    public static DateFormat dtFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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


    public static String formatDate(Date date){
        if (date == null){
            return null;
        }
        return dtFmt.format(date);
    }


}
