package io.recommendation.common.mapper;

import io.recommendation.common.bean.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovieMapper {
    List<Movie> findAllMovie();

    List<Movie> findMovieByType(@Param("type") String type);

    Movie findMovieById(@Param("id")Long id);
}
