package com.lxf.baseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import  org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lxf
 * @create: 2020-04-21 21:08
 * @description: 不在使用springboot自定义的异常处理机制(BasicErrorController)，用于处理系统自定义的异常
 */
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public Map<String ,Object> handlerException(SystemException ex){
        Map<String ,Object> map =new HashMap<>();
        map.put("msg",ex.getMsg());
        map.put("modlue",ex.getModlue());
        return map;
    }
}
