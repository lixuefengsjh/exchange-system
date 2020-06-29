package com.lxf.security.image;

import com.lxf.config.SecurityProperties;
import com.lxf.security.code.ValidateCodeGenerate;
import com.lxf.vo.dto.ImagesCode;
import lombok.Data;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author: lxf
 * @create: 2020-06-27 10:16
 * @description: 图片验证码生成逻辑
 */
public class ImageValidateCodeGenerate implements ValidateCodeGenerate {

    private SecurityProperties securityProperties;

    private   Long expireTime;

    private  int charLength;

    private   int w;

    private  int h ;
    public ImageValidateCodeGenerate(SecurityProperties securityProperties,ServletRequestAttributes s){
        this.securityProperties=securityProperties;
        this.expireTime =securityProperties.getCode().getImg().getExpireTime();
        this.charLength=   securityProperties.getCode().getImg().getCharLength();
        this.w= ServletRequestUtils.getIntParameter(s.getRequest(),"width",securityProperties.getCode().getImg().getW());
        this.h=ServletRequestUtils.getIntParameter(s.getRequest(),"hight",securityProperties.getCode().getImg().getH());
    }
    @Override
    public  ImagesCode generate(){
        String code=getChar();
        BufferedImage images=getImage(code);
        return  new ImagesCode(code, expireTime, images);
    }

    private  String getChar(){
        StringBuffer code=new StringBuffer();
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int size = base.length();
        // 随机产生4位数字的验证码。
        for (int i = 0; i <  charLength; i++) {
            // 得到随机产生的验证码数字。
            int start = random.nextInt(size);
            String strRand = base.substring(start, start + 1);
            code.append(strRand);
        }
        return code.toString();
    }

    private  BufferedImage getImage(String code) {
        BufferedImage image = createImage();// 创建图片缓冲区
        Graphics2D g2 = (Graphics2D) image.getGraphics();// 得到绘制环境
        StringBuilder sb = new StringBuilder();// 用来装载生成的验证码文本
        // 向图片中画4个字符
        for (int i = 0; i < code.length(); i++) {// 循环四次，每次生成一个字符
            String s =code.substring(i,i+1);
            float x = i * 1.0F * w / 4; // 设置当前字符的x轴坐标
            g2.setFont(randomFont()); // 设置随机字体
            g2.setColor(randomColor()); // 设置随机颜色
            g2.drawString(s, x, h - 5); // 画图
        }
        drawLine(image); // 添加干扰线
        return image;
    }
    // 创建BufferedImage
    private  BufferedImage createImage() {
        // 背景色
        Color bgColor = new Color(255, 255, 255);
        BufferedImage image = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(bgColor);
        g2.fillRect(0, 0, w, h);
        return image;
    }
    // 生成随机的字体
    private  Font randomFont() {
        String[] fontNames =
                { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
        Random r = new Random();
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];// 生成随机的字体名称
        int style = r.nextInt(4);// 生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
        int size = r.nextInt(5) + 24; // 生成随机字号, 24 ~ 28
        return new Font(fontName, style, size);
    }
    // 生成随机的颜色
    private   Color randomColor() {
        Random r = new Random();
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }
    // 画干扰线
    private   void drawLine(BufferedImage image) {
        int num = 3;// 一共画3条
        Random r = new Random();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {// 生成两个点的坐标，即4个值
            int x1 = r.nextInt(w);
            int y1 = r.nextInt(h);
            int x2 = r.nextInt(w);
            int y2 = r.nextInt(h);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE); // 干扰线是蓝色
            g2.drawLine(x1, y1, x2, y2);// 画线
        }
    }
}
