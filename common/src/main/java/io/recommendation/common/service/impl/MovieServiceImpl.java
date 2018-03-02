package io.recommendation.common.service.impl;

import io.recommendation.common.bean.Movie;
import io.recommendation.common.mapper.MovieMapper;
import io.recommendation.common.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> findAllMovie() {
        return movieMapper.findAllMovie();
    }

    @Override
    public List<Movie> findMovieByType(String type) {
        return movieMapper.findMovieByType(type);
    }

    @Override
    public Movie findMovieById(Long id) {
        return movieMapper.findMovieById(id);
    }
}
