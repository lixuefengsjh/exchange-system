package com.lxf.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: lxf
 * @create: 2020-04-21 22:11
 * @description: 这个interceptor主要功能是增强功能
 */
@Slf4j
@Component
public class ExpandInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入{}拦截器中","ExpandInterceptor");
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
       if(!(handler instanceof  HandlerMethod)  ){
            return;
       }
        HandlerMethod method=(HandlerMethod)handler;
        log.info("拦截器拦截的handler对应的方法未：{}",method.getBean().getClass().getName());
        log.info("拦截器拦截的handler对应的方法未：{}",method.getMethod().getName());
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        log.info("离开{}拦截器中","ExpandInterceptor");
        log.info(ex+"");
    }
}
