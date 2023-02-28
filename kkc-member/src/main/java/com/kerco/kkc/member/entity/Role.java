package com.kerco.kkc.member.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 简单角色表，主要用来验证管理员
 */
@ToString
@Data
public class Role {
    private Integer id;
    private String name;
}
