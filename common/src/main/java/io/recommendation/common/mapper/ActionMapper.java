package io.recommendation.common.mapper;

import io.recommendation.common.bean.ActionRecord;
import io.recommendation.common.bean.ActionType;
import org.apache.ibatis.annotations.Param;

public interface ActionMapper {

    ActionType findActionType(@Param("type")String type);

    int insertAction(@Param("actionRecord")ActionRecord actionRecord);


}
