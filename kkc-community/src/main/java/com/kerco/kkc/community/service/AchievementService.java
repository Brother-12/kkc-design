package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.vo.UserAchievementVo;

import java.util.concurrent.ExecutionException;

public interface AchievementService {

    /**
     * 获取用户成就信息
     * @param id 用户id
     * @return 用户成就信息
     */
    UserAchievementVo getUserAchievement(Long id) throws ExecutionException, InterruptedException;
}
