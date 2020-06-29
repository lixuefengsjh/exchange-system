package com.lxf.config.codeProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: lxf
 * @create: 2020-06-27 09:56
 * @description: 图片验证码基本配置
 */
@Data
public class ImgCodeProperties extends BaseCodeProperties {

    private   int w = 70;

    private  int h = 35;

}
