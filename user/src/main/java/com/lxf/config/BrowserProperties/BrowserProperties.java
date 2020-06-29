package com.lxf.config.BrowserProperties;

import lombok.Data;

/**
 * @author: lxf
 * @create: 2020-06-26 17:00
 * @description: 浏览器相关配置
 */
@Data
public class BrowserProperties {
    private String loginPage="/page/login/login.html";
    //记住我的过期时间为3周
    private int tokenValiditySeconds= 3*7*24*3600;

}
