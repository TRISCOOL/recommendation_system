package io.recommendation.engine.function;

import com.google.gson.Gson;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

public class TransfromationType implements MapFunction<Row,Row>{
    @Override
    public Row call(Row row) throws Exception {

        //Row resultRow = RowFactory.create(row.get(0),new Gson().toJson(row.get(1)));
        String[] values = new String[]{row.get(0).toString(),new Gson().toJson(row.get(1))};
        return RowFactory.create(values[0],values[1]);
    }
}
