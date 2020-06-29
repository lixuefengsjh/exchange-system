package com.lxf.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.validation.ConstraintValidator;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: lxf
 * @create: 2020-04-21 22:41
 * @description: 增强方法
 */
@Aspect
public class UserAspect  {

    @Around("execution(*  com.lxf..*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("time aspect start");

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is "+arg);
        }

        LocalDateTime start = LocalDateTime.now();

        Object object = pjp.proceed();
        LocalDateTime end = LocalDateTime.now();
        System.out.println("time aspect 耗时:"+ (Duration.between(start,end).getSeconds()));

        System.out.println("time aspect end");

        return object;
    }
}
