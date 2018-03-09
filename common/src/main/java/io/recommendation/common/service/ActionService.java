package io.recommendation.common.service;

import io.recommendation.common.bean.ActionRecord;

public interface ActionService {
    Integer insertOneAction(Long movieId,Long userId,String type);
}
