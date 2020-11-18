package com.dong.mingjiuzhang.controller;

import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.config.aop.limiter.ApiRateLimiter;
import com.dong.mingjiuzhang.global.constant.Constant;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 登入登出
 **/
@RestController
@RequestMapping("")
public class LoginController extends BaseController {

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ApiRateLimiter(value = 0.5)
    public ResponseResult login(HttpServletRequest request) {
        // 保存用户id至session
        // 或者利用jwt生成token返回客户端，下次请求时放在请求头中
        request.getSession().setAttribute(Constant.USER_ID, 0L);
        User user = new User();
        user.setId(1L);
        user.setName("");
        String token = JwtUtil.createToken(user);
        return success(token);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        // 清空session中缓存的用户信息
        request.getSession().removeAttribute(Constant.USER_ID);
        return success();
    }
}
