package com.dong.mingjiuzhang.global.config.aop.permission;

import com.dong.mingjiuzhang.global.enums.RoleEnum;

import java.lang.annotation.*;

/**
 * @Author caishaodong
 * @Date 2020-09-18 14:19
 * @Description
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    RoleEnum[] role();
}
