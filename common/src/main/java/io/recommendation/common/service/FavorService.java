package io.recommendation.common.service;

import io.recommendation.common.bean.Favor;

import java.util.List;

public interface FavorService {

    boolean insertOneFavor(Favor favor);

    Favor findFavorByUserAndMovie(Long movieId,Long userId);

    List<Favor> findFavorByMovie(Long movieId);
}
