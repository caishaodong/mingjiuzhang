package com.dong.mingjiuzhang.global.constant;

/**
 * @Author caishaodong
 * @Date 2020-11-25 20:46
 * @Description
 **/
public class OrderConstants {
    /**
     * 订单前缀
     */
    public static String ORDER_PREFIX = "DD_";
    /**
     * 拼团订单前缀
     */
    public static String GROUP_ORDER_PREFIX = "GD_";
    /**
     * 拼团人数
     */
    public static Integer GROUP_COUNT = 3;
    /**
     * 订单有效时长
     */
    public static Long ORDER_EFFECTIVE_SECONDS = 30 * 60L;
    /**
     * 拼团订单有效时长
     */
    public static Long GROUP_ORDER_EFFECTIVE_SECONDS = 24 * 60 * 60L;
}
