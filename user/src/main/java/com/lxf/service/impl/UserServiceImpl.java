package com.lxf.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lxf.baseEntity.SystemException;
import com.lxf.dao.UserInfoDao;
import com.lxf.service.UserService;
import com.lxf.vo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: lxf
 * @create: 2020-05-13 22:26
 * @description:
 */
@Service
public class UserServiceImpl implements UserService , UserDetailsService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int save(UserInfo userInfo) {
        int i=userInfoDao.save(userInfo);
        return i;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user=userInfoDao.findUserByName(username).orElseThrow(
                ()-> new SystemException("用户名不存在","user")
        );
       Set<String>  roles=userInfoDao.findUserRoleByUserId(user.getId());
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities=roles.stream().map(e->
            new SimpleGrantedAuthority(e)
        ).collect(Collectors.toSet());
        return new User(user.getName(), passwordEncoder.encode(user.getPassword()), simpleGrantedAuthorities);
    }
}
