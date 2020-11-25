package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.domain.entity.dto.PreOrderDTO;
import com.dong.mingjiuzhang.domain.entity.vo.OrderSaveVO;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
public interface OrderService extends IService<Order> {

    /**
     * 保存订单
     *
     * @param preOrderDTO
     * @return
     */
    OrderSaveVO saveOrder(PreOrderDTO preOrderDTO);
}
