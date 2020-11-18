package com.dong.mingjiuzhang.global.util.geo;

/**
 * @Author caishaodong
 * @Date 2020-10-22 15:36
 * @Description mysql根据经纬度查询附近的人
 **/
public class MysqlGeoUtil {
    /**使用sql可以直接查询距离最近的数据
     *
     * SELECT city, lng,lat,
     * (POWER(MOD(ABS(lng - 116.3658730),360),2) + POWER(ABS(lat - 39.9122350),2)) AS distance
     * FROM `p_sys_city`
     * ORDER BY distance LIMIT 100
     */
}
