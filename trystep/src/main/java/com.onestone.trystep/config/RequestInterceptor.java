package com.onestone.trystep.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/3 9:46
 */

public class RequestInterceptor extends HandlerInterceptorAdapter {
    private final static Logger logger = LoggerFactory.getLogger("RequestInterceptor");
    private final String BTIME = "beginTime";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long time = System.currentTimeMillis();
        request.setAttribute(BTIME,time);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long)request.getAttribute(BTIME);
        request.removeAttribute(BTIME);
        long endTime = System.currentTimeMillis();
        long elapsed = 0;
        request.setAttribute("timeelapse",(elapsed=(endTime-startTime)));
        logger.info(request.getRequestURI()+":"+elapsed);
    }
}
