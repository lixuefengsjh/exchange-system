package com.lxf.security.sms;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.lxf.config.SecurityProperties;
import com.lxf.security.code.ValidateCodeGenerate;
import com.lxf.vo.dto.Code;
import com.lxf.vo.dto.ImagesCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author: lxf
 * @create: 2020-06-28 10:50
 * @description: 短信验证码生成器
 */
@Slf4j
public class SmsValidateCodeGenerate  implements ValidateCodeGenerate {
    private  SecurityProperties securityProperties;
    public SmsValidateCodeGenerate (SecurityProperties securityProperties){
        this.securityProperties=securityProperties;
    }
    @Override
    public Code generate() {
        log.info(String.format("生成手机验证码，时间为%tF %<tT %n",new Date()));
        String randomCode= StrUtil.sub(UUID.randomUUID().toString().toLowerCase(),0,securityProperties.getCode().getSms().getCharLength());
        log.info(String.format("生成手机验证码为:---%s",randomCode));
        Code code=new Code(randomCode,securityProperties.getCode().getSms().getExpireTime());
        return code;
    }
}
