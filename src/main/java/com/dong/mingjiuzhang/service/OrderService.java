package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Order;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
public interface OrderService extends IService<Order> {

    Order getOkById(Long id);

    /**
     * 获取订单编号
     *
     * @return
     */
    String getOrderSn();

    /**
     * 获取拼团订单编号
     *
     * @return
     */
    String getGroupOrderSn();

    /**
     * 获取当前该系列待拼团订单
     *
     * @param courseSeriesId
     * @param userId
     * @return
     */
    Order getGroupOrder(Long courseSeriesId, Long userId);

    /**
     * 拼团人数加1
     *
     * @param orderId
     * @return
     */
    int incrCurrentGroupCount(Long orderId);

    /**
     * 根据订单编号获取订单
     *
     * @param orderSn
     * @return
     */
    Order getByOrderSn(String orderSn);


}
