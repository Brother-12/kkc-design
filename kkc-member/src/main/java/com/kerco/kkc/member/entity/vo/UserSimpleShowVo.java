package com.kerco.kkc.member.entity.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Data
public class UserSimpleShowVo {
    //用户id
    @NotNull(message = "id不能为空")
    private Long id;
    //用户名
    private String username;
    //性别
    @Length(min = 1,max = 1)
    @NotBlank(message = "sex不能为空")
    private String sex;
    //关注数
    private Integer followerCount;
    //粉丝数
    private Integer followedCount;
    //头像
    private String avatar;
    //个人简介
    private String summary;
    //创建时间
    private LocalDateTime createTime;
}
