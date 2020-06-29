package com.lxf.vo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author: lxf
 * @create: 2020-04-27 10:10
 * @description: 用户信息:这里只是适当利用校验
 */
@Data
@NoArgsConstructor
public class UserInfo {

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "登陆密码不能为空")
    private String password;

    @NotBlank(message="手机号码不能为空")
    @Size(message = "手机号码不合规",min=11,max = 11)
    private String phone;

    @PastOrPresent(message = "创建日期不能为将来时间")
    private LocalDateTime createTime;

    private String photo;

}
