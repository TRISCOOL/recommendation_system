package io.recommendation.stream.model;

import java.io.Serializable;

public class Rank implements Serializable {
    private String movieId;
    private Integer score;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
