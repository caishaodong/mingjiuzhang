package com.dong.mingjiuzhang.global.util.geo;

import java.text.DecimalFormat;

/**
 * @Author caishaodong
 * @Date 2020-10-22 14:32
 * @Description 计算两个经纬度之间的距离
 **/
public class DisUtil {
    /**
     * 赤道半径(单位m)
     */
    private static final double EARTH_RADIUS = 6378137;

    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 基于余弦定理求两经纬度距离
     *
     * @param lon1 第一点的经度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的经度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double lantitudeLongitudeDist(double lon1, double lat1,
                                                double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double radLon1 = rad(lon1);
        double radLon2 = rad(lon2);

        if (radLat1 < 0) {
            // south
            radLat1 = Math.PI / 2 + Math.abs(radLat1);
        }
        if (radLat1 > 0) {
            // north
            radLat1 = Math.PI / 2 - Math.abs(radLat1);
        }
        if (radLon1 < 0) {
            // west
            radLon1 = Math.PI * 2 - Math.abs(radLon1);
        }
        if (radLat2 < 0) {
            // south
            radLat2 = Math.PI / 2 + Math.abs(radLat2);
        }
        if (radLat2 > 0) {
            // north
            radLat2 = Math.PI / 2 - Math.abs(radLat2);
        }
        if (radLon2 < 0) {
            // west
            radLon2 = Math.PI * 2 - Math.abs(radLon2);
        }
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);

        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);

        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        //余弦定理求夹角
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        double dis = theta * EARTH_RADIUS;

        return formatDis(dis);
    }


    public static Double formatDis(double distance) {
        System.out.println(distance);
        DecimalFormat df = new DecimalFormat("#.00");
        Double dis = new Double(df.format(distance));
        return dis;
    }

    public static void main(String[] args) {

        double lon1 = 109.0145193757;
        double lat1 = 34.236080797698;
        double lon2 = 108.9644583556;
        double lat2 = 34.286439088548;
        double dist = DisUtil.lantitudeLongitudeDist(lon1, lat1, lon2, lat2);

        System.out.println("两点相距：" + dist + " 米");
    }
}
