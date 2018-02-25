package io.recommendation.common.mapper;

import io.recommendation.common.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User getUserByAccount(@Param("account")String account);
}
