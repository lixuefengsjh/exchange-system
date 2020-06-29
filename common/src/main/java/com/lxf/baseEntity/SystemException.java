package com.lxf.baseEntity;

import lombok.Data;

/**
 * @author: lxf
 * @create: 2020-04-19 22:44
 * @description: 自定义系统错误
 */
@Data
public class SystemException  extends  RuntimeException{
    private String msg;
    private String modlue;
    public SystemException(String msg ,String modlue){
        super(msg);
        this.modlue=modlue;
    }
}
