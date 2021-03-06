package io.recommendation.web.controller;

import com.google.gson.reflect.TypeToken;
import io.recommendation.common.bean.ActionType;
import io.recommendation.common.bean.MovieFavor;
import io.recommendation.common.bean.User;
import io.recommendation.common.service.ActionService;
import io.recommendation.common.service.impl.RedisService;
import io.recommendation.common.util.Util;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.producer.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController {

    protected static final String AUTHORIZATION = "Authorization";

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    private static KafkaSender kafkaSender = new KafkaSender();

    @Resource
    private RedisService redisService;

    @Autowired
    private ActionService actionService;

    @ExceptionHandler
    @ResponseBody
    public ResponseVo defaultExceptionHandler(Exception ex) {
        ResponseVo vo = ResponseVo.error(Code.SERVER_ERROR,ex.getMessage());
/*        if (ex instanceof TAutoException) {
            TAutoException tae = (TAutoException) ex;
            vo = ResponseVO.error(tae.getCode());
        }*/
        if (ex instanceof BindException) {
            BindException be = (BindException) ex;
            if (be.getBindingResult() != null) {
                List<FieldError> fieldErrors = be.getBindingResult().getFieldErrors();
                StringBuilder sb = new StringBuilder();
                fieldErrors.stream().forEach(fieldError -> {
                    String msg = fieldError.getField() + fieldError.getDefaultMessage();
                    sb.append(msg);
                    sb.append(",");
                });
                vo = ResponseVo.error(Code.PARAM_ILLEGAL, sb.toString());
            }
        }
        logger.error("", ex);
        return vo;
    }


    public User getUserByAuthRequire(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        if (auth == null){
            return null;
        }

        String userJson = redisService.getStr(auth);
        if (userJson == null || "".equals(userJson))
            return null;

        User user = Util.jsonToObject(userJson,new TypeToken<User>(){});
        return user;
    }

    public void persistActionAndsendMessage(Long movieId,Long userId,String type){
        Integer score = actionService.insertOneAction(movieId,userId,type);
        MovieFavor movieFavor = new MovieFavor();
        movieFavor.setUserId(userId);
        movieFavor.setMovieId(movieId);
        movieFavor.setScore(score);

        String json = Util.objectToJson(movieFavor);
        kafkaSender.sendMessage(userId.toString(),json);
    }


}
