package com.dong.mingjiuzhang.global.util.file.excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author caishaodong
 * @Date 2020-10-10 09:07
 * @Description
 **/
public class TailMapListUtil {
    /**
     * 获取订单列表导出Excel的追加行
     *
     * @param deliveryUserName
     * @param checkUserName
     * @param totalAmount
     * @return
     */
    public static List<Map<Integer, Object>> getTailMapList(String deliveryUserName, String checkUserName, BigDecimal totalAmount) {
        List<Map<Integer, Object>> tailMapList = new ArrayList<>();
        Map<Integer, Object> map1 = new HashMap<>(16);
        map1.put(0, "总计");
        map1.put(9, totalAmount);
        tailMapList.add(map1);
        Map<Integer, Object> map2 = new HashMap<>(16);
        map2.put(1, "送货人：");
        map2.put(2, deliveryUserName);
        map2.put(5, "验收人：");
        map2.put(6, checkUserName);
        tailMapList.add(map2);
        return tailMapList;
    }
}
