package io.recommendation.engine.model;

import java.io.Serializable;

public class Rating implements Serializable{
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    private Long userId;
    private Long movieId;
    private Integer rating;

    public Rating(){}

    public Rating(Long userId, Long movieId, Integer rating){
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }



}
