package com.lxf.controller;

import com.lxf.baseEntity.ResponseResult;
import com.lxf.config.SecurityProperties;
import com.lxf.security.image.ImageValidateCodeGenerate;
import com.lxf.security.sms.SmsValidateCodeGenerate;
import com.lxf.security.code.ValidateCodeGenerate;
import com.lxf.vo.dto.Code;
import com.lxf.vo.dto.ImagesCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Date;


/**
 * @author: lxf
 * @create: 2020-05-15 16:31
 * @description: 登陆相关处理
 */
@RestController
@RequestMapping("/login/")
@Slf4j
public class LoginController {
    private RequestCache  requestCache=new HttpSessionRequestCache();
    private RedirectStrategy  redirectStrategy=new DefaultRedirectStrategy();
    private SessionStrategy   sessionStrategy =new HttpSessionSessionStrategy();
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 获取图片验证码
     * lxf:进一步优化，需要实现以下3点内容
     * 1.验证码参数可配置：参数》配置》默认
     * 2.
     */
    @GetMapping("images/code")
    public void getImagesCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ValidateCodeGenerate imageGenerate=new ImageValidateCodeGenerate(securityProperties,new ServletWebRequest(req));
        ImagesCode image= (ImagesCode)imageGenerate.generate();
        sessionStrategy.setAttribute(new ServletWebRequest(req),"img",image);
        log.info(String.format("生成的图片验证码为：%s",image.getCode()));
        // HttpSession session =  req.getSession();
        //session.setAttribute("img",image);
        ImageIO.write((RenderedImage) image.getImages(),"jpg",resp.getOutputStream());
    }

    /**
     * 手机登陆，获取手机号验证码
     */
    @GetMapping("sms/code")
    public  void getSmsCode(String phoneNumber,HttpServletRequest req){
        ValidateCodeGenerate smsGenerate=new SmsValidateCodeGenerate(securityProperties);
        Code smsCode=smsGenerate.generate();
        sessionStrategy.setAttribute(new ServletWebRequest(req),"sms",smsCode);
        log.info(String.format("短信发送成功，你的手机上已接受到短信验证码，为 %s",smsCode.getCode()));
    }


    @RequestMapping("authentication/required")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult requiredAuthentication(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        log.info(String.format("进入requiredAuthentication方法时间为%tF %<tT%n",new Date()));
        SavedRequest savedRequest= requestCache.getRequest(req,resp);
        if(null!=savedRequest&&savedRequest.getRedirectUrl().endsWith(".html")){
            /**
             * 这里希望实现2个功能
             * 1.如果用户自定义登陆页面，跳转自定义，否则跳转默认页面
             * 2.登陆的时候，如果是html请求，会直接跳转到登陆页面，否则提示
             * 3.这里有一个注意事项：savedRequest 只是正对存在且受保护的url，否则将永远为null
             */
            redirectStrategy.sendRedirect(req,resp,securityProperties.getBrowser().getLoginPage());
        }
        return new ResponseResult("未进行登陆",302,"未进行登陆");

    }
    public ResponseResult login(String phone,String smsCode){

        return ResponseResult.ok();
    }
}