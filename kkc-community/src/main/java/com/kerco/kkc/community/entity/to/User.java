package com.kerco.kkc.community.entity.to;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@ToString
@Data
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("性别 男、女")
    private String sex;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("个人简介")
    private String summary;

    @ApiModelProperty("状态 0:启用 1:禁用")
    private Integer status;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
