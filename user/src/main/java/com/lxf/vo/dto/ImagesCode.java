package com.lxf.vo.dto;

import com.lxf.vo.dto.Code;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author: lxf
 * @create: 2020-06-11 19:39
 * @description: 图片验证码
 */
@Setter
@Getter
public class ImagesCode extends Code {

    private BufferedImage images;

    public ImagesCode(String code, Long ms,BufferedImage images) {
        super(code, ms);
        this.images=images;
    }
}
