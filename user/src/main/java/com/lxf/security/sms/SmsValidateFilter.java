package com.lxf.security.sms;

import cn.hutool.core.util.StrUtil;
import com.lxf.baseEntity.SystemException;
import com.lxf.config.SecurityProperties;
import com.lxf.security.LoginFailedHandler;
import com.lxf.vo.dto.Code;
import com.lxf.vo.dto.ImagesCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: lxf
 * @create: 2020-06-29 14:17
 * @description: 短信码验证功能
 */
@Component
@Slf4j
public class SmsValidateFilter implements Filter, InitializingBean {
    @Autowired
    private LoginFailedHandler loginFailedHandler;
    @Autowired
    private SecurityProperties securityProperties;
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    private PathMatcher pathMatcher=  new AntPathMatcher();
    private Set<String> paths=new HashSet<>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(String.format("初始化SmsValidateFilter成功，时间为%tF %<tT%n",new Date()));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequestAttributes servlet=new ServletWebRequest((HttpServletRequest) servletRequest);
        Boolean flag=false;
        for(String p:paths){
            if(pathMatcher.match(p,servlet.getRequest().getRequestURI())){
                flag=true;
                break;
            }
        }
        if(flag){
            try {
                validateCode(servlet);
            }catch (SystemException e){
                AuthenticationException ex=new AccountExpiredException(e.getMsg());
                loginFailedHandler.onAuthenticationFailure((HttpServletRequest) servletRequest,(HttpServletResponse) servletResponse,ex);
                return;
            }
            filterChain.doFilter( servletRequest,  servletResponse);
        }else {
            filterChain.doFilter( servletRequest,  servletResponse);
        }



    }

    private void validateCode(ServletRequestAttributes servlet) throws ServletRequestBindingException {
        String code= ServletRequestUtils.getStringParameter(servlet.getRequest(),"smsCode");
        Code sms= (Code) sessionStrategy.getAttribute(servlet,"sms");
        if(null==sms){
            throw  new SystemException("请输入验证码","user");
        }else if(sms.isExpire()){
            sessionStrategy.removeAttribute(servlet,"img");
            throw  new SystemException("验证码已过期，请重新输入","user");
        }else  if(StrUtil.isEmpty(code)){
            sessionStrategy.removeAttribute(servlet,"img");
            throw  new SystemException("验证码不能为空","user");
        }else if(!StrUtil.equalsIgnoreCase(code,sms.getCode())){
            sessionStrategy.removeAttribute(servlet,"img");
            throw  new SystemException("输入的验证码不正确，请重新输入","user");
        }
        sessionStrategy.removeAttribute(servlet,"img");
    }

    @Override
    public void destroy() {
        log.info(String.format("销毁ImageValidateFilter成功，时间为%tF %<tT%n",new Date()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] ps=  StrUtil.split(securityProperties.getCode().getImg().getPaths(),",");
        for(String  p:ps){
            paths.add(p);
        }
        paths.add("/login/login/sms");
    }
}
