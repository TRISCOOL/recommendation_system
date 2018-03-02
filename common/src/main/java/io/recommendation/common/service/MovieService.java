package io.recommendation.common.service;

import io.recommendation.common.bean.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAllMovie();

    List<Movie> findMovieByType(String type);

    Movie findMovieById(Long id);
}
