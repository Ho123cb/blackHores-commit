package com.heima.wemedia.interrupt;


import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.AppJwtUtil;
import com.heima.utils.thread.WmThreadLocalUtil;
import io.jsonwebtoken.Claims;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 用于存储用户id
 * 应用于：
 * 1. 当上传图片需要用户id
 */
@Slf4j
public class WmMateriaInterrupt implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdStr = request.getHeader("userId");
        Optional<String> optional = Optional.ofNullable(userIdStr);
        if (optional.isPresent() && StringUtils.isNotBlank(userIdStr)) {
            Integer userId = Integer.valueOf(userIdStr);
            WmUser user = new WmUser();
            user.setApUserId(userId);
            WmThreadLocalUtil.setUser(user);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("清理threadlocal...");
        WmThreadLocalUtil.clear();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
