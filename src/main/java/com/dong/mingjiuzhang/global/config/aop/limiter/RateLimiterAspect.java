package com.dong.mingjiuzhang.global.config.aop.limiter;

import com.dong.mingjiuzhang.global.config.aop.permission.PermissionAspect;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author caishaodong
 * @Date 2020-10-10 15:16
 * @Description
 **/
@Component
@Aspect
@Scope
@Order(value = 0)
public class RateLimiterAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionAspect.class);
    private static final Map<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    @Pointcut(value = "@annotation(com.dong.mingjiuzhang.global.config.aop.limiter.ApiRateLimiter)")
    public void rateLimit() {
    }

    @Around("rateLimit()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        Signature signature = proceedingJoinPoint.getSignature();
        String methodName = signature.getName();
        LOGGER.info("RateLimiterAspect: className:{},methodName:{}", className, methodName);

        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiRateLimiter.class)) {
            // 取过别名的注解属性，必须有Spring自身来解析注解
            ApiRateLimiter apiRateLimiter = AnnotationUtils.findAnnotation(method, ApiRateLimiter.class);
            double qps = apiRateLimiter.qps();

            if (qps > ApiRateLimiter.NOT_LIMITED) {
                RateLimiter rateLimiter;
                if (Objects.isNull(rateLimiter = RATE_LIMITER_CACHE.get(methodName))) {
                    // 初始化RateLimiter
                    rateLimiter = RateLimiter.create(qps);
                    RATE_LIMITER_CACHE.put(methodName, rateLimiter);
                }
                if (!rateLimiter.tryAcquire(apiRateLimiter.timeout(), apiRateLimiter.timeUnit())) {
                    LOGGER.error("流量访问过大: className:{},methodName:{}", className, methodName);
                    throw new BusinessException(BusinessEnum.REQUEST_RATE_LIMIT);
                }

            }
        }
        return proceedingJoinPoint.proceed();
    }

}
