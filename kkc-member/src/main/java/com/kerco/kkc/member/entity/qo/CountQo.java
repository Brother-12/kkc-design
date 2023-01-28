package com.kerco.kkc.member.entity.qo;

import lombok.Data;
import lombok.ToString;

/**
 * 记录 用户关注数和粉丝数
 */
@ToString
@Data
public class CountQo {
//        关注数
    private Integer followerCount;
//    粉丝数
    private Integer followedCount;
}
