package com.lxf.config;

import com.lxf.config.BrowserProperties.BrowserProperties;
import com.lxf.config.codeProperties.CodeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: lxf
 * @create: 2020-06-26 16:59
 * @description: 安全配置类
 */
@ConfigurationProperties(prefix = "com.lxf")
@Data
public class SecurityProperties {
    private BrowserProperties browser=new BrowserProperties();
    private CodeProperties code=new CodeProperties();
}
