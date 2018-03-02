package io.recommendation.common.service.impl;

import io.recommendation.common.bean.Favor;
import io.recommendation.common.mapper.FavorMapper;
import io.recommendation.common.service.FavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavorServiceImpl implements FavorService{

    @Autowired
    private FavorMapper favorMapper;

    @Override
    public boolean insertOneFavor(Favor favor) {
        int result = favorMapper.insertOneFavor(favor);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public Favor findFavorByUserAndMovie(Long movieId, Long userId) {
        return favorMapper.findFavorByUserAndMovie(movieId,userId);
    }

    @Override
    public List<Favor> findFavorByMovie(Long movieId) {
        return favorMapper.findFavorByMovie(movieId);
    }
}
