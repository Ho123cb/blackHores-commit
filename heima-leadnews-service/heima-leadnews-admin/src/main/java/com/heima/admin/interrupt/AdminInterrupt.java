package com.heima.admin.interrupt;


import com.heima.model.admin.pojos.AdUser;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.thread.AdminThreadLocalUtil;
import com.heima.utils.thread.WmThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
public class AdminInterrupt implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdStr = request.getHeader("userId");
        Optional<String> optional = Optional.ofNullable(userIdStr);
        if (optional.isPresent() && StringUtils.isNotBlank(userIdStr)) {
            Integer userId = Integer.valueOf(userIdStr);
            AdUser user = new AdUser();
            user.setId(userId);
            AdminThreadLocalUtil.setUser(user);
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
