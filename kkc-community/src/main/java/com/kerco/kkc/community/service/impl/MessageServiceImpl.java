package com.kerco.kkc.community.service.impl;

import com.kerco.kkc.community.entity.Message;
import com.kerco.kkc.community.mapper.MessageMapper;
import com.kerco.kkc.community.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
