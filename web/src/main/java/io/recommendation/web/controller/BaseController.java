package io.recommendation.web.controller;

import io.recommendation.common.service.impl.RedisService;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    private RedisService redisService;

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


}
