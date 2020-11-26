package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.OrderGroupUser;

import java.util.List;

/**
 * <p>
 * 拼团订单用户表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
public interface OrderGroupUserService extends IService<OrderGroupUser> {

    /**
     * 获取当前拼团的用户信息
     *
     * @param orderId
     * @return
     */
    List<OrderGroupUser> getByOrderId(Long orderId);

    /**
     * 根据订单id和团购订单编号获取团购订单
     *
     * @param orderId
     * @param groupOrderSn
     * @return
     */
    OrderGroupUser getByOrderIdAndGroupOrderSn(Long orderId, String groupOrderSn);

    /**
     * 根据团购订单编号获取团购订单
     *
     * @param groupOrderSn
     * @return
     */
    OrderGroupUser getByGroupOrderSn(String groupOrderSn);
}
