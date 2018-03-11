package io.recommendation.common.mapper;

import io.recommendation.common.bean.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovieMapper {
    List<Movie> findAllMovie();

    List<Movie> findMovieByType(@Param("type") String type,@Param("offset")Integer offset,@Param("length")Integer length);

    Movie findMovieById(@Param("id")Long id);

    List<Movie> findSimilarById(@Param("id") Long id);
}
