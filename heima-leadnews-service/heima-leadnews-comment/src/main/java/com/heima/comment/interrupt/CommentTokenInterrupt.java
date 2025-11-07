package com.heima.comment.interrupt;


import com.heima.model.user.pojos.ApUser;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.thread.AppThreadLocalUtil;
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
 */
@Slf4j
public class CommentTokenInterrupt implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdStr = request.getHeader("userId");
        Optional<String> optional = Optional.ofNullable(userIdStr);
        if (optional.isPresent() && StringUtils.isNotBlank(userIdStr)) {
            Integer userId = Integer.valueOf(userIdStr);
            ApUser user = new ApUser();
            user.setId(userId.longValue());
            AppThreadLocalUtil.setUser(user);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("清理threadlocal...");
        AppThreadLocalUtil.clear();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
