package com.dong.mingjiuzhang.global.base;


import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Author caishaodong
 * @Date 2020-09-07 12:21
 * @Description 基础类
 **/
public class BaseController {
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public ResponseResult success() {
        return ResponseResult.success();
    }

    public ResponseResult success(Object t) {
        return ResponseResult.success(t);
    }

    public ResponseResult error() {
        return ResponseResult.error();
    }

    public ResponseResult error(BusinessEnum businessEnum) {
        return ResponseResult.error(businessEnum);
    }

    /**
     * 非空校验
     *
     * @param params
     */
    public static void assertNotEmpty(String... params) {
        for (int i = 0; i < params.length; i++) {
            Assert.isTrue(params[i] != null && params[i].trim().length() > 0, BusinessEnum.PARAM_ERROR.getDesc());
        }
    }

    /**
     * 非空校验
     *
     * @param collection
     */
    public static void assertNotEmpty(Collection collection) {
        Assert.notEmpty(collection, BusinessEnum.PARAM_ERROR.getDesc());
    }

    /**
     * 非空校验
     *
     * @param params
     */
    public static void assertNotNull(Object... params) {
        for (int i = 0; i < params.length; i++) {
            Assert.notNull(params[i], BusinessEnum.PARAM_ERROR.getDesc());
        }
    }

    public static void main(String[] args) {
        try {
            assertNotEmpty("", "");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
