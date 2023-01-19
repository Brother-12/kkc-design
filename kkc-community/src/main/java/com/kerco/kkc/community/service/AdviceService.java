package com.kerco.kkc.community.service;

import com.kerco.kkc.community.entity.Advice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.community.utils.PageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface AdviceService extends IService<Advice> {

    /**
     * 分页获取所有意见反馈信息
     *
     * 请求参数解锁currentPage
     * currentPage：当前页数，不是必须
     * @return 分页 意见反馈信息
     */
    PageUtils getAdviceList(Integer page);
}
