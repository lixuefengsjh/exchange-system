package com.lxf.controller;

import com.lxf.service.UserService;
import com.lxf.vo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: lxf
 * @create: 2020-05-13 22:09
 * @description: 用户相关操作
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userServiceImpl;
    @PostMapping
    public int saveUserInfo(@RequestBody  @Valid UserInfo userInfo){
       int i= userServiceImpl.save(userInfo);
       return  i;
    }
    @GetMapping
    public Object getLoginUserInfo(){
        return   SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
