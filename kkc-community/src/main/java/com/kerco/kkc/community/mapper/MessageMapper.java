package com.kerco.kkc.community.mapper;

import com.kerco.kkc.community.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
}
