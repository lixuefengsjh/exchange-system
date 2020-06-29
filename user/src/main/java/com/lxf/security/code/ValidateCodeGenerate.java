package com.lxf.security.code;

import com.lxf.vo.dto.Code;
import com.lxf.vo.dto.ImagesCode;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: lxf
 * @create: 2020-06-27 10:15
 * @description: 验证码生成器
 */
public interface ValidateCodeGenerate {
    Code generate();
}
