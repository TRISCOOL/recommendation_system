package io.recommendation.common.service.impl;

import io.recommendation.common.bean.Movie;
import io.recommendation.common.bean.Rank;
import io.recommendation.common.mapper.RankMapper;
import io.recommendation.common.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService{

    @Autowired
    private RankMapper rankMapper;

    @Override
    public int insertOrUpdate(Rank rank) {
        Rank lastRank = rankMapper.findByMovieId(rank.getMovieId());

        int result = 0;

        if (lastRank == null){
            result = rankMapper.insert(rank);
        }else {
            result = rankMapper.update(rank);
        }

        return result;
    }

    @Override
    public List<Movie> ranks() {
        return rankMapper.ranks();
    }
}
