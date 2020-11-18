package com.dong.mingjiuzhang.global.config.aop.permission;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.RoleEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 14:21
 * @Description 权限校验切面
 **/
@Component
@Aspect
@Order(value = 2)
public class PermissionAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionAspect.class);

    @Pointcut(value = "@annotation(com.dong.mingjiuzhang.global.config.aop.permission.Permission)")
    public void permission() {
    }

    @Around("permission()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        Signature signature = proceedingJoinPoint.getSignature();
        String methodName = signature.getName();
        LOGGER.info("PermissionAspect: className:{},methodName:{}", className, methodName);

        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(Permission.class)) {
            // 可根据请求用户token获取用户id
            Integer userId = 1;
            Permission permission = method.getAnnotation(Permission.class);
            RoleEnum[] role = permission.role();
            if (Objects.nonNull(role)) {
                // 接口允许的角色
                List<RoleEnum> requiredUserPermission = new ArrayList<>(Arrays.asList(role));
                LOGGER.info("请求当前接口需要的用户角色:{}", requiredUserPermission);
                // 根据用户id从数据库中查询权限
                List<RoleEnum> currentUserPermission = queryPermission(userId);
                LOGGER.info("当前用户的权限:" + currentUserPermission);
                // 取交集，判断是否拥有权限
                requiredUserPermission.retainAll(currentUserPermission);
                if (CollectionUtils.isEmpty(requiredUserPermission)) {
                    // 交集为空，没有访问权限
                    LOGGER.error("没有访问权限");
                    throw new BusinessException(BusinessEnum.NO_ACCESS_PERMISSION);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * 获取用户权限
     *
     * @param userId
     * @return
     */
    private List<RoleEnum> queryPermission(Integer userId) {
        List<RoleEnum> list = new ArrayList<>();
        list.add(RoleEnum.OTHER);
        return list;
    }

}
