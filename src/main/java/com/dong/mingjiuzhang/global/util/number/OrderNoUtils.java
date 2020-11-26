package com.dong.mingjiuzhang.global.util.number;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author caishaodong
 * @Date 2020-09-10 16:01
 * @Description
 **/
public class OrderNoUtils {
    protected static final AtomicLong ORDER_SEQ = new AtomicLong(1000);
    protected static final AtomicLong GROUP_ORDER_SEQ = new AtomicLong(1000);
    protected static final String PATTERN = "yyyyMMdd";
    protected static final DateTimeFormatter FORMATTER;
    static OrderNoUtils orderNoUtils;

    public static OrderNoUtils getInstance() {
        if (OrderNoUtils.orderNoUtils == null) {
            return OrderNoUtils.orderNoUtils = new OrderNoUtils();
        }
        return OrderNoUtils.orderNoUtils;
    }

    /**
     * 获取订单编号
     *
     * @return
     */
    public static String getOrderSerialNumber() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return OrderNoUtils.FORMATTER.format(LocalDateTime.now()).substring(2, 8) + ORDER_SEQ.getAndIncrement() + String.format("%010d", hashCode);
    }

    /**
     * 获取拼团订单编号
     *
     * @return
     */
    public static String getGroupOrderSerialNumber() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return OrderNoUtils.FORMATTER.format(LocalDateTime.now()).substring(2, 8) + GROUP_ORDER_SEQ.getAndIncrement() + String.format("%010d", hashCode);
    }

    static {
        FORMATTER = DateTimeFormatter.ofPattern(PATTERN);
        OrderNoUtils.orderNoUtils = new OrderNoUtils();
    }

    public static void main(String[] args) {
        List<String> orderNos = Collections.synchronizedList(new ArrayList<String>());
        IntStream.range(0, 100).parallel().forEach(i -> {
            orderNos.add(getOrderSerialNumber());
        });

        Collections.sort(orderNos);

        List<String> filterOrderNos = orderNos.stream().distinct().collect(Collectors.toList());

        System.out.println(orderNos);

        System.out.println("生成订单数：" + orderNos.size());
        System.out.println("过滤重复后订单数：" + filterOrderNos.size());
        System.out.println("重复订单数：" + (orderNos.size() - filterOrderNos.size()));
    }
}
