package com.kerco.kkc.member.controller.front;

import com.google.code.kaptcha.Producer;
import com.kerco.kkc.common.utils.CommonResult;
import com.kerco.kkc.member.entity.vo.UserAuthVo;
import com.kerco.kkc.member.entity.vo.UserRegisterVo;
import com.kerco.kkc.member.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * 认证请求控制器
 */
@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private AccessService accessService;

    @Autowired
    private Producer producer;

    /**
     * 登陆接口
     * @return 登陆结果
     */
    @PostMapping("/login")
    public CommonResult login(@RequestBody UserAuthVo authVo,HttpServletRequest request,HttpServletResponse response){
        String token = accessService.authentication(authVo,request);

        /*在设置了解决跨域之后，默认在前台只能获取
            Content-Language、Content-Type、Expires、Last-Modified、Pragma
            五个response header 值。

            所以需要将你设置的自定义header键值给暴露出来。
        */
        response.setHeader("Access-Control-Expose-Headers","accessKey");
        response.setHeader("accessKey",token);

        return CommonResult.success("登陆成功");
    }

    /**
     * 注册接口
     * @return 注册结果
     */
    @PostMapping("/register")
    public CommonResult register( @RequestBody UserRegisterVo userRegisterVo,HttpServletResponse response){
        String token = accessService.registerUser(userRegisterVo);

        response.setHeader("Access-Control-Expose-Headers","accessKey");
        response.setHeader("accessKey",token);

        return CommonResult.success("注册成功");
    }

    /**
     * 退出登录
     * @param request HttpServletRequest
     * @return 退出登录结果
     */
    @PostMapping("/logout")
    public CommonResult logout(HttpServletRequest request){
        accessService.logout(request);

        return CommonResult.success();
    }

    @GetMapping("/code")
    public String getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //1.获取验证码
        String text = producer.createText();
        //2.保存到session 或 redis
        request.getSession().setAttribute("vcode",text);
        //3.生成图片
        BufferedImage image = producer.createImage(text);
        //4.返回base64，前端显示的话需要拼接上 "data:image/jpg;base64,"
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(image,"jpg",fos);
        return "data:image/jpg;base64,"+ Base64.getEncoder().encodeToString(fos.toByteArray());
    }

    /**
     * 查看请求中是否有携带token，有则处理
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    public CommonResult getUserInfo(HttpServletRequest request){
        Map<String,Object> result = accessService.getUserInfo(request);

        return CommonResult.success(result);
    }

    /**
     * 管理员登录
     * @param userAuthVo
     * @return
     */
    @PostMapping("/login/admin111")
    public CommonResult adminLogin(@RequestBody UserAuthVo userAuthVo,HttpServletResponse response){
        String token = accessService.adminLogin(userAuthVo);

        response.setHeader("Access-Control-Expose-Headers","accessKey");
        response.setHeader("accessKey",token);

        return CommonResult.success("登录成功");
    }
}
