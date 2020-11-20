package com.dong.mingjiuzhang.global.config.aop.login;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:10
 * @Description 登录校验切面
 **/
@Configuration
@Aspect
@Order(value = 1)
public class LoginAspect implements InitializingBean {
    @Value("${free.login.uri}")
    private String FREE_LOGIN_URI;

    /**
     * 免登URI集合
     */
    private List<String> FREE_LOGIN_URI_LIST = new ArrayList<>();

    @Pointcut("execution(public * com.dong.mingjiuzhang.controller.*.*(..))")
    public void login() {
    }

    @Around("login()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();

        Long userId = getUserId(request);
        if (!FREE_LOGIN_URI_LIST.contains(requestURI) && Objects.isNull(userId)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * 获取用户id
     *
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request) {
        return getUserIdFromRequestHeader(request);
    }

    /**
     * 从token中获取userId
     *
     * @param request
     * @return
     */
    public Long getUserIdFromRequestHeader(HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.TOKEN_HEADER);
        Long userId = StringUtil.isBlank(token) ? null : JwtUtil.getUserIdByToken(token);
        return userId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtil.isNotBlank(FREE_LOGIN_URI)) {
            FREE_LOGIN_URI_LIST = Arrays.asList(FREE_LOGIN_URI.split(","));
        }
    }
}
