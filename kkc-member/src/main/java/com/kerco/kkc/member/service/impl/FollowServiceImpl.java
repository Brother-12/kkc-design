package com.kerco.kkc.member.service.impl;

import com.kerco.kkc.common.utils.JwtUtils;
import com.kerco.kkc.member.entity.Follow;
import com.kerco.kkc.member.entity.vo.FollowVo;
import com.kerco.kkc.member.mapper.FollowMapper;
import com.kerco.kkc.member.service.FollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    /**
     * 检查登陆的用户是否有关注该用户
     *
     * 思路：
     *  1.将传输过来的userId与header的token保存的用户id进行比较，如果不相同，直接返回0
     *  2.如果传输过来的userId与followId相同，则表示自己关注自己，直接返回0
     *  3.将userId与followId去数据库搜索，返回Follow对象，如果不为空同时状态不为1，则表示以前有关注过
     * @param followVo 检查关注的信息
     * @param request HttpServletRequest
     * @return 0：没关注 1：关注
     */
    @Override
    public int checkUserFollow(FollowVo followVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Object> result = JwtUtils.getPayLoadALSOExcludeExpAndIat(token);
        Long userId = Long.parseLong(result.get("id").toString());
        if(!followVo.getUserId().equals(userId) || followVo.getUserId().equals(followVo.getFollowId())){
            return 0;
        }else{
            Follow follow = followMapper.checkUserFollow(userId, followVo.getFollowId());
            if(Objects.isNull(follow) || follow.getStatus() == 1){
                return 0;
            }else{
                return 1;
            }
        }
    }

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
    @Override
    public int userConfirmFollow(FollowVo followVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Object> result = JwtUtils.getPayLoadALSOExcludeExpAndIat(token);
        Long userId = Long.parseLong(result.get("id").toString());
        if(!followVo.getUserId().equals(userId) || followVo.getUserId().equals(followVo.getFollowId())){
            return 0;
        }else{
            Follow follow = followMapper.checkUserFollow(userId, followVo.getFollowId());
            if(Objects.isNull(follow)){
                Follow follow1 = new Follow();
                follow1.setFollowerId(followVo.getFollowId());
                follow1.setFollowedId(userId);
                followMapper.userConfirmFollow(follow1);
            }else{
                followMapper.updateFollowStatus(follow.getId(),0);
            }
        }
        return 1;
    }

    /**
     * 用户取消关注
     * 思路：取消关注前需要先检查数据，与checkUserFollow思路一样
     *      一切检查没问题后，检查userId和getFollowId查询数据库后返回的对象，如果为空，说明没有关注过，直接返回0
     *      如果不为空，则修改status为1即可
     * @param followVo 关注的信息
     * @param request HttpServletRequest
     */
    @Override
    public int userCancelFollow(FollowVo followVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Object> result = JwtUtils.getPayLoadALSOExcludeExpAndIat(token);
        Long userId = Long.parseLong(result.get("id").toString());
        if(!followVo.getUserId().equals(userId) || followVo.getUserId().equals(followVo.getFollowId())){
            return 0;
        }else {
            Follow follow = followMapper.checkUserFollow(userId, followVo.getFollowId());
            if (Objects.isNull(follow)) {
                return 0;
            } else {
                followMapper.updateFollowStatus(follow.getId(), 1);
            }
        }
        return 1;
    }
}
