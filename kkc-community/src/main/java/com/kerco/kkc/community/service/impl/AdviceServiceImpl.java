package com.kerco.kkc.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kerco.kkc.community.entity.Advice;
import com.kerco.kkc.community.mapper.AdviceMapper;
import com.kerco.kkc.community.service.AdviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerco.kkc.community.utils.PageUtils;
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
public class AdviceServiceImpl extends ServiceImpl<AdviceMapper, Advice> implements AdviceService {
    @Override
    public PageUtils getAdviceList(Integer currentPage) {
        Page<Advice> page = null;
        //这里使用条件构造器来构建sql语句，配合我们使用的分页插件，生成分页数据
        QueryWrapper<Advice> wrapper = new QueryWrapper<>();

        //这里对create_time进行降序操作，将最新的意见放到最前面
        wrapper.orderByDesc("create_time");

        //如果当前页为空，则从第一页开始查找
        if(currentPage == null){
            page = this.page(new Page<Advice>(1, 7), wrapper);
        }else{
            page = this.page(new Page<Advice>(currentPage, 7), wrapper);
        }
        return new PageUtils(page);
    }
}
