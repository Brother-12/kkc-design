package com.kerco.kkc.member.service;

import com.kerco.kkc.member.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kerco.kkc.member.entity.vo.FollowVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
public interface FollowService extends IService<Follow> {

    /**
     * 检查登陆的用户是否有关注该用户
     * @param followVo 检查关注的信息
     * @param request HttpServletRequest
     * @return 结果
     */
    int checkUserFollow(FollowVo followVo, HttpServletRequest request);

    /**
     * 用户关注
     *
     * 思路：关注前需要先检查数据，与checkUserFollow思路一样
     *      在一切没问题时，检查userId和getFollowId查询数据库后返回的对象，如果为空，则表示以前没有关注过，需要插入一条关注数据
     *      如果不为空，则表示以前对status状态改为1，则修改状态为0即可
     * @param followVo 关注的信息
     * @param request HttpServletRequest
     * @return 1：关注成功 0：关注失败
     */
    int userConfirmFollow(FollowVo followVo, HttpServletRequest request);

    /**
     * 用户取消关注
     * 思路：取消关注前需要先检查数据，与checkUserFollow思路一样
     *      一切检查没问题后，检查userId和getFollowId查询数据库后返回的对象，如果为空，说明没有关注过，直接返回0
     *      如果不为空，则修改status为1即可
     * @param followVo 关注的信息
     * @param request HttpServletRequest
     */
    int userCancelFollow(FollowVo followVo, HttpServletRequest request);
}
