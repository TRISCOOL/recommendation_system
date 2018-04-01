package io.recommendation.engine.model;

import java.io.Serializable;

public class Result implements Serializable{
    private Integer userId;
    private String recommendations;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
