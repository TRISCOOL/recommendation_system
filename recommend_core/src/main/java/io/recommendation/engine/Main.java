package io.recommendation.engine;

import io.recommendation.engine.model.Rating;
import io.recommendation.engine.source.HbaseDataSource;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String TABLE_NAME = "reconmmend_relationship";
    private static final String COLUMNS_FAMILY = "movie_favor";

    public static void main(String[] args){
        try {
            //获取hbase链接
            HbaseDataSource hbaseDataSource = new HbaseDataSource("192.168.102.3:2181,192.168.102.13:2181,192.168.102.24:2181");

            SparkSession spark = SparkSession.builder().appName("recommend").getOrCreate();
            //while (true){
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Rating> ratings = hbaseDataSource.getDataSource(TABLE_NAME,COLUMNS_FAMILY);
                System.out.println("get data:"+ratings.size());
                Dataset<Row> ratingsWithdf = spark.createDataFrame(ratings,Rating.class);
                Dataset<Row>[] split = ratingsWithdf.randomSplit(new double[]{0.8,0.2});

                Dataset<Row> training = split[0];
                Dataset<Row> test = split[1];

                ALS als = new ALS()
                        .setMaxIter(10)
                        .setRegParam(0.01)
                        .setUserCol("userId")
                        .setItemCol("movieId")
                        .setRatingCol("rating");

                //ALSModel model = als.fit(training);
                ALSModel model = als.fit(ratingsWithdf);
                model.setColdStartStrategy("drop");

/*                Dataset<Row> predictions = model.transform(test);
                RegressionEvaluator evaluator = new RegressionEvaluator()
                        .setMetricName("rmse")
                        .setLabelCol("rating")
                        .setPredictionCol("prediction");*/

/*                Double rmse = evaluator.evaluate(predictions);
                System.out.println("Root-mean-square error = " + rmse);*/

                Dataset<Row> userRecs = model.recommendForAllUsers(10);
                Dataset<Row> itemRecs = model.recommendForAllItems(10);

                userRecs.show();
                itemRecs.show();

                spark.stop();

            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
