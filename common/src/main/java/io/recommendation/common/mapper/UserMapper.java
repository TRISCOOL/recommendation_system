package io.recommendation.common.mapper;

import io.recommendation.common.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User findUserByAccount(@Param("account")String account);

    int insert(@Param("user")User user);
}
