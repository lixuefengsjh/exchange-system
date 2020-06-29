package com.lxf.config.codeProperties;

import lombok.Data;

/**
 * @author: lxf
 * @create: 2020-06-28 10:56
 * @description: 验证码基本配置
 */
@Data
public class BaseCodeProperties {
    private   Long expireTime=5*60*1000L;

    private  int charLength=4;

    private String paths;
}
