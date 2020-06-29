package com.lxf.Filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

/**
 * @author: lxf
 * @create: 2020-04-21 21:50
 * @description: 统计handler的耗时情况
 */
@Slf4j
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TimeFilter 初始化完成");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        LocalDateTime start=LocalDateTime.now();
        chain.doFilter(req,res);
        LocalDateTime end=LocalDateTime.now();
        log.info("TimeFilter统计handler耗时{}", Duration.between(start,end).getSeconds());
    }

    @Override
    public void destroy() {
        log.info("TimeFilter 销毁");
    }
}
