package com.dong.mingjiuzhang.global.util.reflect;


import com.dong.mingjiuzhang.global.enums.YesNoEnum;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @Author caishaodong
 * @Date 2020-09-07 18:04
 * @Description
 **/
public class ReflectUtil {

    public static void setCreateInfo(Object obj, Class<?> clazz) {
        try {
            LocalDateTime now = LocalDateTime.now();
            ReflectUtil.setObjectValue(obj, clazz, "gmtCreate", now);
            ReflectUtil.setObjectValue(obj, clazz, "gmtModified", now);
            ReflectUtil.setObjectValue(obj, clazz, "isDeleted", YesNoEnum.NO.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setObjectValue(Object obj, Class<?> clazz, String name, Object value) throws Exception {
        Field created = clazz.getDeclaredField(name);
        if (created != null) {
            created.setAccessible(true);
            created.set(obj, value);
        }
    }
}
