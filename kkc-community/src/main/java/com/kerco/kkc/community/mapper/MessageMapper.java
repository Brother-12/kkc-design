package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerco.kkc.community.entity.vo.MessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 保存消息
     * @param message 待保存的消息
     */
    void saveMessage(Message message);

    /**
     * 获取消息列表
     * @param currentPage 当前页数
     * @return 消息列表
     */
    List<MessageVo> getMessage(@Param("userId") Long userId, @Param("page") Integer currentPage);
}
