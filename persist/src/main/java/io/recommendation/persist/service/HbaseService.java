package io.recommendation.persist.service;

import java.io.IOException;

public interface HbaseService {
    void putdata(String tableName,String rowKey,String colunmsFamily,String colunmsQualifier,String value) throws IOException;

    String getValue(String tableName,String rowKey,String colunmsFamily,String colunmsQualifier) throws IOException;

    void close();
}
