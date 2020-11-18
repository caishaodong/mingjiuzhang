package com.dong.mingjiuzhang.global.config.aop.limiter;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author caishaodong
 * @Date 2020-10-10 15:16
 * @Description
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiRateLimiter {
    double NOT_LIMITED = 0;

    /**
     * qps
     */
    @AliasFor("qps")
    double value() default NOT_LIMITED;

    /**
     * qps
     */
    @AliasFor("value")
    double qps() default NOT_LIMITED;

    /**
     * 超时时长
     * 默认间隔1s时间
     */
    long timeout() default 1000L;

    /**
     * 超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
