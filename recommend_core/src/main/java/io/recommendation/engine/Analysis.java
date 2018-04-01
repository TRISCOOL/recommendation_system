package io.recommendation.engine;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class Analysis {

    private static final String JDBC_URL = "jdbc:mysql://192.168.102.24/recommendation?useUnicode=true&characterEncoding=UTF-8";
    private static final String TABLE = "SELECT u.id AS user_id,m.id AS movie_id,u.age,u.sex,m.type from action_record ar LEFT JOIN movie m ON ar.movie_id=m.id LEFT JOIN `user` u ON ar.user_id = u.id GROUP BY user_id,movie_id";

    public static void main(String[] args){

        SparkSession spark = SparkSession.builder().appName("analysis").getOrCreate();

        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "password");
        Dataset<Row> source = spark.read().jdbc(JDBC_URL,TABLE,properties);
        source.show();

        Dataset<Row> countByAge = source.groupBy("age","type").count();
        Dataset<Row> countBySex = source.groupBy("sex","type").count();
        spark.stop();
    }
}
