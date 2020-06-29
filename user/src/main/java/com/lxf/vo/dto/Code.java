package com.lxf.vo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: lxf
 * @create: 2020-06-11 19:37
 * @description: 验证码
 */
@Setter
@Getter
public class Code {
    private String code;

    private LocalDateTime expireTime;

    public Code(String code,Long ms){
        this.code=code;
        this.expireTime=LocalDateTime.now().plusSeconds(ms);
    }

    public Boolean isExpire(){
        return expireTime.isBefore(LocalDateTime.now());
    }
}
