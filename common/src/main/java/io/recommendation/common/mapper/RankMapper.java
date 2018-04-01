package io.recommendation.common.mapper;

import io.recommendation.common.bean.Movie;
import io.recommendation.common.bean.Rank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RankMapper {

    int insert(@Param("rank")Rank rank);

    int update(@Param("rank")Rank rank);

    Rank findByMovieId(@Param("movieId")Long movieId);

    List<Movie> ranks();
}
