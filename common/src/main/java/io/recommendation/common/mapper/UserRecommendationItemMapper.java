package io.recommendation.common.mapper;

import io.recommendation.common.bean.UserRecommendationItem;
import org.apache.ibatis.annotations.Param;

public interface UserRecommendationItemMapper {

    UserRecommendationItem selectByUserId(@Param("userId")String userId);
}
