package com.kerco.kkc.common.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 只保存用户的关键信息
 */
@ToString
@Data
public class UserKeyTo {
    private Long id;

    private String username;

    private String avatar;
}
