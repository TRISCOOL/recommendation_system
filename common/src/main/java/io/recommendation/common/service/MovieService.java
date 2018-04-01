package io.recommendation.common.service;

import io.recommendation.common.bean.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> findAllMovie();

    List<Movie> findMovieByType(String type,Integer offset,Integer length);

    Movie findMovieById(Long id);

    List<Movie> findSimilarById(Long id);

    Map<String,Integer> analysisForTypeCount();

    Map<String,Integer> analysisForSexCount();

    List<Movie> getRecommendationsForUser(Long userId);

    Map<String,List<Integer>> analysisForTX();


}
