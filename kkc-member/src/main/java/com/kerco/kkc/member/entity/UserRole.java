package com.kerco.kkc.member.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserRole {

    private Integer id;

    private Long userId;

    private Integer roleId;
}
