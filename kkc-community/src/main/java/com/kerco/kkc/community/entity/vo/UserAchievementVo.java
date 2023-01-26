package com.kerco.kkc.community.entity.vo;

import lombok.*;

/**
 * 前台 用户成就展示类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserAchievementVo {

    //原创数
    private Integer originalCount = 0;

    //点赞数
    private Integer thumbsUpCount = 0;

    //粉丝数
    private Integer followedCount = 0;

    //收藏数
    private Integer collectionCount = 0;

    public void setOriginalCount(Integer originalCount) {
        this.originalCount += originalCount;
    }

    public void setThumbsUpCount(Integer thumbsUpCount) {
        this.thumbsUpCount += thumbsUpCount;
    }

    public void setFollowedCount(Integer followedCount) {
        this.followedCount = followedCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }
}
