package com.lxf.config.codeProperties;

import lombok.Data;

/**
 * @author: lxf
 * @create: 2020-06-27 10:06
 * @description: 验证码配置项
 */
@Data
public class CodeProperties {
    private ImgCodeProperties img=new ImgCodeProperties();
    private BaseCodeProperties sms=new BaseCodeProperties();
}
