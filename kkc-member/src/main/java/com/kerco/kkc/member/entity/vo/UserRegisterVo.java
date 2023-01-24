package com.kerco.kkc.member.entity.vo;

import lombok.Data;
import lombok.ToString;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ToString
@Data
public class UserRegisterVo {

    //用户名
    @NotEmpty(message = "用户名不能为空")
    @Length(message = "用户名长度必须在4到16之间",min = 4,max= 16)
    private String username;
    //密码
    @NotEmpty(message = "密码不能为空")
    @Length(message = "密码最少6位",min = 6)
    private String password;
    //邮箱
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱地址格式不正确")
    private String email;
    //验证码
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
