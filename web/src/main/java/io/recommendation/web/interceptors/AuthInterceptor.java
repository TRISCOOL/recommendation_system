package io.recommendation.web.interceptors;

import io.recommendation.common.service.impl.RedisService;
import io.recommendation.common.util.Util;
import io.recommendation.common.vo.Code;
import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.annotations.AuthRequire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String AUTHORIZATION = "Authorization";


    @Resource
    private RedisService redisService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AuthRequire auth = handlerMethod.getMethodAnnotation(AuthRequire.class);
            if (auth == null) {
                return true;
            }
            String authorization = request.getHeader(AUTHORIZATION);
            try {
                if (StringUtils.isEmpty(authorization)) {
                    return authFail(response);
                }
                if (redisService == null) {
                    redisService = (RedisService) WebApplicationContextUtils.getRequiredWebApplicationContext(request
                            .getServletContext()).getBean("redisService");
                }
                if (!redisService.exists(authorization)) {
                    return authFail(response);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return authFail(response);
            }
        } else {
            return true;
        }
    }

    private boolean authFail(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try (OutputStream outputStream = response.getOutputStream()) {
            ResponseVo vo = ResponseVo.error(Code.AUTHORIZED);
            outputStream.write(Util.objectToJson(vo).getBytes());
        } catch (IOException e) {
            logger.error("auth fail", e);
            return false;
        }
        return false;
    }
}