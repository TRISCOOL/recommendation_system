package io.recommendation.persist.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HbaseServiceImpl implements HbaseService {

    private Connection connection;

    public HbaseServiceImpl(String hbaseServer){
        if (connection != null)return;
        synchronized (this){
            if (connection != null)return;
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum",hbaseServer);

            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void putdata(String tableName,String rowKey,String colunmsFamily,String colunmsQualifier,String value)
            throws IOException{
        if (connection == null){
            return;
        }

        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(colunmsFamily),Bytes.toBytes(colunmsQualifier), Bytes.toBytes(value));
        table.put(put);
        table.close();

    }

    @Override
    public String getValue(String tableName, String rowKey, String colunmsFamily, String colunmsQualifier) throws IOException {
        if (connection == null){
            return null;
        }

        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        if (get == null)return null;

        Result result = table.get(get);
        byte[] values = result.getValue(Bytes.toBytes(colunmsFamily),Bytes.toBytes(colunmsQualifier));
        if (values == null || values.length <= 0) return null;
        table.close();
        return Bytes.toString(values);
    }

    @Override
    public void close() {
        if (connection == null)return;
        synchronized (this){
            if (connection == null)return;
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
