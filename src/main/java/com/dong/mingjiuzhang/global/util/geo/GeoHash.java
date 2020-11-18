package com.dong.mingjiuzhang.global.util.geo;

import java.util.BitSet;
import java.util.HashMap;

/**
 * @Author caishaodong
 * @Date 2020-10-22 14:52
 * @Description GeoHash对经纬度编码
 **/
public class GeoHash {
    private static int numbits = 6 * 5;
    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    final static HashMap<Character, Integer> lookup = new HashMap<>();

    static {
        int i = 0;
        for (char c : digits) {
            lookup.put(c, i++);
        }
    }

    public double[] decode(String geohash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geohash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        //even bits
        int j = 0;
        for (int i = 0; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length()) {
                isSet = buffer.charAt(i) == '1';
            }
            lonset.set(j++, isSet);
        }

        //odd bits
        j = 0;
        for (int i = 1; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length()) {
                isSet = buffer.charAt(i) == '1';
            }
            latset.set(j++, isSet);
        }
        //中国地理坐标：东经73°至东经135°，北纬4°至北纬53°
        double lon = decode(lonset, 70, 140);
        double lat = decode(latset, 0, 60);

        return new double[]{lon, lat};
    }

    private double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i)) {
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return mid;
    }


    public String encode(double lon, double lat) {
        BitSet lonbits = getBits(lon, 70, 140);
        BitSet latbits = getBits(lat, 0, 60);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append((lonbits.get(i)) ? '1' : '0');
            buffer.append((latbits.get(i)) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    private BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    public static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative) {
            i = -i;
        }
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (65 - charPos));
    }

    public static void main(String[] args) {
        double lon1 = 109.0145193757;
        double lat1 = 34.236080797698;
        double lon2 = 108.9644583556;
        double lat2 = 34.286439088548;
        String geocode;

        GeoHash geohash = new GeoHash();
        geocode = geohash.encode(lon1, lat1);
        System.out.println("当前位置编码：" + geocode);
        System.out.println(geohash.decode(geocode)[0]);

        geocode = geohash.encode(lon2, lat2);
        System.out.println("远方位置编码：" + geocode);
        System.out.println(geohash.decode(geocode)[0]);
    }

}
