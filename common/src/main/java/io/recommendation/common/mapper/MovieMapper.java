package io.recommendation.common.mapper;

import io.recommendation.common.bean.AnasisyForSex;
import io.recommendation.common.bean.AnasisyForType;
import io.recommendation.common.bean.Movie;
import io.recommendation.common.bean.WatchRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MovieMapper {
    List<Movie> findAllMovie();

    List<Movie> findMovieByType(@Param("type") String type,@Param("offset")Integer offset,@Param("length")Integer length);

    Movie findMovieById(@Param("id")Long id);

    List<Movie> findSimilarById(@Param("id") Long id);

    List<AnasisyForType> analysisForTypeCount();

    List<AnasisyForSex> analysisForSexCount();

    List<Movie> selectMoviesByIds(@Param("movieIds")List<Long> movieIds);

    List<WatchRecord> findWatchRecord();
}
