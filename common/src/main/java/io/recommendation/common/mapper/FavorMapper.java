package io.recommendation.common.mapper;

import io.recommendation.common.bean.Favor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavorMapper {

    int insertOneFavor(@Param("favor")Favor favor);

    Favor findFavorByUserAndMovie(@Param("movieId")Long movieId,@Param("userId")Long userId);

    List<Favor> findFavorByMovie(@Param("movieId")Long movieId);
}
