package io.recommendation.common.service;

import io.recommendation.common.bean.Movie;
import io.recommendation.common.bean.Rank;

import java.util.List;

public interface RankService {

    int insertOrUpdate(Rank rank);

    List<Movie> ranks();
}
