package com.lxf.baseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: lxf
 * @create: 2020-04-26 10:30
 * @description: 返回格式
 */
@Data
@AllArgsConstructor
public class ResponseResult<T> {
    private T content;
    private int code;
    private String msg;

    public static ResponseResult ok() {
        return  new ResponseResult("",200,"服务调用成功");
    }
}
