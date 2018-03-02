package io.recommendation.common.mapper;

import io.recommendation.common.bean.ActionType;
import org.apache.ibatis.annotations.Param;

public interface ActionMapper {

    ActionType findActionTypeByType(@Param("type")String type);
}
