//package com.lxf.service.impl;
//
//import com.lxf.baseEntity.SystemException;
//import com.lxf.dao.UserInfoDao;
//import com.lxf.service.PermissionAccessService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Set;
//
///**
// * @author: lxf
// * @create: 2020-05-16 10:15
// * @description: 鉴权功能实现
// */
//@Component
//public class PermissionAccessServiceImpl implements PermissionAccessService {
//    private AntPathMatcher matcher=new AntPathMatcher();
//
//    @Autowired
//    private UserInfoDao userInfoDao;
//    @Override
//    public Boolean hasPermission(HttpServletRequest req, Authentication auth) {
//        Set<SimpleGrantedAuthority> authorities=(Set<SimpleGrantedAuthority>)auth.getAuthorities();
//        String url=req.getRequestURI();
//        Boolean hasPermission=false;
//        for(SimpleGrantedAuthority e:authorities){
//            if(matcher.match(e.getAuthority(),url)){
//                hasPermission=true;
//            }
//            break;
//        }
//        return hasPermission;
//    }
//}
