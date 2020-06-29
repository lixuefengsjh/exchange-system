//package com.lxf.security;
//
//import org.springframework.context.support.MessageSourceAccessor;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.SpringSecurityMessageSource;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.util.AntPathMatcher;
//
//import java.util.Collection;
//import java.util.Set;
//
///**
// * @author: lxf
// * @create: 2020-05-18 08:57
// * @description: 自定义投票管理器
// */
//public class MyAccessDecisionManager implements AccessDecisionManager {
//    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
//    private AntPathMatcher pathMatcher=new AntPathMatcher() ;
//
//    @Override
//    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
//        Set<SimpleGrantedAuthority> authorities= (Set<SimpleGrantedAuthority>) authentication.getAuthorities();
//        FilterInvocation fi =(FilterInvocation)o;
//        String url=fi.getRequest().getRequestURI();
//        Boolean hasPermission=false;
//        for(SimpleGrantedAuthority e:authorities){
//            if(pathMatcher.match(e.getAuthority(),url)){
//                return;
//            }
//        }
//        throw new AccessDeniedException(this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
//    }
//
//
//    @Override
//    public boolean supports(ConfigAttribute configAttribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}
