package io.recommendation.stream.model;

import java.io.Serializable;

public class Rating implements Serializable{
    private Long userId;
    private Long movieId;
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

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

    public Rating(){}

    public Rating(Long userId, Long movieId, Integer score){
        this.userId = userId;
        this.movieId = movieId;
        this.score = score;
    }



}
