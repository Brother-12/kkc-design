package com.kerco.kkc.community.controller;

import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.community.service.AdviceService;
import com.kerco.kkc.community.utils.PageUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kerco
 * @since 2023-01-12
 */
@Api(value = "AdviceController",tags = {"建议类"})
@RestController
@RequestMapping("/community/advice")
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    /**
     * 分页获取所有意见反馈信息
     *
     * 请求参数解锁currentPage
     * currentPage：当前页数，不是必须
     * @return 分页 意见反馈信息
     */
    @GetMapping("/list")
    public CommonResult getUserList(@RequestParam(value = "currentPage",required = false) Integer page){
        PageUtils adviceList = adviceService.getAdviceList(page);

        CommonResult<PageUtils> result = CommonResult.success(adviceList);
        return result;
    }
}
