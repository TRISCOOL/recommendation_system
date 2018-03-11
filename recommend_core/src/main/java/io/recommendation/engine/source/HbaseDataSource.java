package io.recommendation.engine.source;

import io.recommendation.engine.model.Rating;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HbaseDataSource {

    private Connection connection;
    private Configuration configuration;

    public HbaseDataSource(String hbaseServer) throws IOException{
        this.configuration = HBaseConfiguration.create();
        this.configuration.set("hbase.zookeeper.quorum",hbaseServer);
        this.connection = ConnectionFactory.createConnection(configuration);
    }

    public List<Rating> getDataSource(String tableName, String columnsFamily) throws IOException{
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        if (table == null)
            return null;

        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(columnsFamily));

        ResultScanner results = table.getScanner(scan);
        List<Rating> ratings = new ArrayList<Rating>();
        for (Result rr = results.next();rr != null;rr = results.next()){
            List<Cell> cells = rr.listCells();
            for (Cell cell : cells){
                String cellInfo = cell.toString();
                Long userId = getUserId(cellInfo);
                Long movieId = getMovieId(cellInfo);
                Integer score = getScore(cell.getValueArray(),cell.getValueOffset(),cell.getValueLength());
                ratings.add(getRating(userId,movieId,score));
            }
        }
        return ratings;
    }

    private Rating getRating(Long userId,Long movieId,Integer score){
        Rating rating = new Rating(userId,movieId,score);
        return rating;
    }

    private void close(Table table) throws IOException{
        table.close();
        this.connection.close();
    }

    private void close(){
        try {
            this.connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Long getUserId(String cellInfo){
        return Long.parseLong(cellInfo.substring(0,1));
    }

    private Long getMovieId(String cellInfo){
        return Long.parseLong(cellInfo.substring(14,15));
    }

    private Integer getScore(byte[] values,Integer offset,Integer length){
        if (offset + length > values.length){
            return null;
        }

        byte[] valueByteArray = new byte[length];
        int num = 0;
        for (int i=offset;i<offset+length;i++){
            valueByteArray[0] = values[i];
            num++;
        }

        return Integer.parseInt(Bytes.toString(valueByteArray));
    }

}
