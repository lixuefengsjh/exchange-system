package com.lxf.utils;

import java.lang.reflect.Field;

/**
 * @author: lxf
 * @create: 2020-06-08 11:35
 * @description: Bean对象工具类
 */
public class BeanUtils {
    public static  Boolean checkObjIsNull(Object o) throws IllegalAccessException {
        Boolean flag=true;
        if(o==null){
            flag=true;
        }else{
            for(Field f:o.getClass().getDeclaredFields()){
                f.setAccessible(true);
                System.out.println(f.getType());
                Object res=f.get(o);
                if(null!=res){
                    flag=false;
                    break;
                };
            }
        }
        return flag;
    }

    public static void main(String[] args) throws IllegalAccessException {
        Book b= new Book();
        b.setName("1212");
        System.out.println(checkObjIsNull(b));
    }
}
