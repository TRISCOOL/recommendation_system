package io.recommendation.common.service.impl;

import io.recommendation.common.bean.ActionRecord;
import io.recommendation.common.bean.ActionType;
import io.recommendation.common.bean.MovieFavor;
import io.recommendation.common.mapper.ActionMapper;
import io.recommendation.common.service.ActionService;
import io.recommendation.common.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService{

    @Autowired
    private ActionMapper actionMapper;

    @Override
    public Integer insertOneAction(Long movieId,Long userId,String type) {

        ActionType actionType = actionMapper.findActionType(type);
        if (actionType == null)return null;

        ActionRecord actionRecord = new ActionRecord();
        actionRecord.setMovieId(movieId);
        actionRecord.setUserId(userId);
        actionRecord.setTypeId(actionType.getId().longValue());

        actionMapper.insertAction(actionRecord);
        
        return actionType.getValue();
    }
}
