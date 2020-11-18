package com.dong.mingjiuzhang.global.util.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @Author caishaodong
 * @Date 2020-09-11 16:08
 * @Description
 **/
public class LocalDateTimeUtil {
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DAY = "DAY";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String MILLI = "MILLI";
    public static final String NANO = "NANO";

    /**
     * 格式化日期时间
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String formatDateTimeByPattern(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    /**
     * 获取今日日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongToday() {
        String today = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        return Long.parseLong(today);
    }

    /**
     * 获取昨日日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongYesterday() {
        String today = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now().plusDays(-1));
        return Long.parseLong(today);
    }

    /**
     * 获取本周一日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongThisMonday() {
        LocalDate thisMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        String thisMondayStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(thisMonday);
        return Long.parseLong(thisMondayStr);
    }

    /**
     * 获取上周一日期（yyyyMMdd）
     *
     * @return
     */
    public static Long getLongLastMonday() {
        LocalDate thisMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate lastMonday = thisMonday.plusDays(-7);
        String lastMondayStr = DateTimeFormatter.ofPattern("yyyyMMdd").format(lastMonday);
        return Long.parseLong(lastMondayStr);
    }

    /**
     * 获取日期中的年月日
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Integer getByLocalDatePattern(String date, String pattern, String type) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDateTime = LocalDate.parse(date, df);

        Integer value;
        switch (type) {
            case YEAR:
                value = localDateTime.getYear();
                break;
            case MONTH:
                value = localDateTime.getMonthValue();
                break;
            case DAY:
                value = localDateTime.getDayOfMonth();
                break;
            default:
                value = null;
        }
        return value;
    }

    /**
     * 获取两个日期之差
     *
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static Long getDays(String dateStr1, String dateStr2) {
        LocalDate startTime = LocalDate.parse("2020-12-30");
        LocalDate endTime = LocalDate.parse("2020-11-03");
        Long days = startTime.until(endTime, ChronoUnit.DAYS);
        System.out.println(startTime + "  与  " + endTime + " 之间相差   " + days + "天");
        return days;
    }

    /**
     * 获取两个日期时间之差
     *
     * @param dateStr1
     * @param dateStr2
     * @param type
     * @return
     */
    public static Long getBetween(String dateStr1, String dateStr2, String type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(dateStr1, formatter);
        LocalDateTime endTime = LocalDateTime.parse(dateStr2, formatter);
        Duration duration = Duration.between(startTime, endTime);
        Long result = null;
        switch (type) {
            case DAY:
                result = duration.toDays();
                break;
            case HOUR:
                result = duration.toHours();
                break;
            case MINUTE:
                result = duration.toMinutes();
                break;
            case MILLI:
                result = duration.toMillis();
                break;
            default:
                result = null;
        }

        return result;
    }

    /**
     * 日期增加几天
     *
     * @param localDate
     * @param days
     * @return
     */
    public static LocalDate plus(LocalDate localDate, int days) {
        LocalDate plus = localDate.plus(Period.ofDays(days));
        return plus;
    }

    /**
     * 日期增加几天
     *
     * @param localDate
     * @param days
     * @return
     */
    public static LocalDate plus(LocalDate localDate, long days) {
        LocalDate plus = localDate.plusDays(days);
        return plus;
    }

    /**
     * 日期时间增加几天
     *
     * @param localDateTime
     * @param days
     * @return
     */
    public static LocalDateTime plus(LocalDateTime localDateTime, int days) {
        LocalDateTime plus = localDateTime.plus(Period.ofDays(days));
        return plus;
    }

    /**
     * 日期时间增加几天
     *
     * @param localDateTime
     * @param days
     * @return
     */
    public static LocalDateTime plus(LocalDateTime localDateTime, long days) {
        LocalDateTime plus = localDateTime.plusDays(days);
        return plus;
    }

    public static void main(String[] args) {
//        System.out.println(getLongToday());
//        System.out.println(getLongYesterday());
//        System.out.println(getLongThisMonday());
//        System.out.println(getLongLastMonday());
//        System.out.println(getByLocalDatePattern("20200230", "yyyyMMdd", LocalDateTimeUtil.DAY));
//        System.out.println(getDays("",""));
        System.out.println(getBetween("2020-11-03 12:00:00", "2020-11-03 12:30:00", HOUR));

//        LocalDate localDate = LocalDate.parse("2020-10-10");
//        System.out.println(plus(localDate, 3L));

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime localDateTime = LocalDateTime.parse("2020-11-03 12:00:00", formatter);
//        System.out.println(plus(localDateTime, 3L));
    }
}
