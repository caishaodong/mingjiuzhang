package com.dong.mingjiuzhang.service.impl;

import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.mapper.OrderMapper;
import com.dong.mingjiuzhang.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
