package com.lxf.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author: lxf
 * @create: 2020-06-08 12:05
 * @description:
 */
@Slf4j
public class ReflectTest {
    public static void main(String[] args) {
        Book b=new Book();
        b.setAuthor("lxxf");
        b.setName("ces");
        Class clazz=null;
        try {
             clazz=Class.forName("com.lxf.utils.Book");
        } catch (ClassNotFoundException e) {
           log.info("未获取到对应的class");
        }
        //获取字段Filed
        for(Field f:clazz.getDeclaredFields()){
            try {
                f.setAccessible(true);
                System.out.println(f.getName());
                System.out.println(f.get(b));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }
}
