package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.domain.entity.OrderGroupUser;
import com.dong.mingjiuzhang.global.constant.OrderConstants;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.GroupStatusEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.number.OrderNoUtils;
import com.dong.mingjiuzhang.mapper.OrderMapper;
import com.dong.mingjiuzhang.service.OrderGroupUserService;
import com.dong.mingjiuzhang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private OrderGroupUserService orderGroupUserService;


    @Override
    public Order getOkById(Long id) {
        return getOne(new LambdaQueryWrapper<Order>().eq(Order::getId, id).eq(Order::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 获取订单编号
     *
     * @return
     */
    @Override
    public String getOrderSn() {
        return OrderConstants.ORDER_PREFIX + OrderNoUtils.getOrderSerialNumber();
    }

    /**
     * 获取拼团订单编号
     *
     * @return
     */
    @Override
    public String getGroupOrderSn() {
        return OrderConstants.GROUP_ORDER_PREFIX + OrderNoUtils.getGroupOrderSerialNumber();
    }

    /**
     * 获取当前该系列待拼团订单
     *
     * @param courseSeriesId
     * @param userId
     * @return
     */
    @Override
    public Order getGroupOrder(Long courseSeriesId, Long userId) {
        List<Order> groupOrderList = this.baseMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getCourseSeriesId, courseSeriesId)
                .eq(Order::getIsGroup, YesNoEnum.YES.getValue())
                .eq(Order::getGroupStatus, GroupStatusEnum.GROUPING.getStatus())
                .eq(Order::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(Order::getId));
        if (CollectionUtils.isEmpty(groupOrderList)) {
            return null;
        }
        Order groupOrder = groupOrderList.get(0);
        // 获取当前拼团的人数
        Integer currentGroupCount = groupOrder.getCurrentGroupCount();
        if (currentGroupCount.intValue() >= OrderConstants.GROUP_COUNT) {
            // 如果当前拼团人数满了，则重新拼团
            return null;
        }

        // 获取当前拼团的用户信息
        List<OrderGroupUser> orderGroupUserList = orderGroupUserService.getByOrderId(groupOrder.getId());
        // 校验当前拼团的人有没有自己
        List<Long> groupUserIdList = orderGroupUserList.stream().map(OrderGroupUser::getUserId).collect(Collectors.toList());
        if (groupUserIdList.contains(userId)) {
            throw new BusinessException(BusinessEnum.ALREADY_GROUPING);
        }
        return groupOrder;
    }

    /**
     * 拼团人数加1
     *
     * @param orderId
     * @return
     */
    @Override
    public int incrCurrentGroupCount(Long orderId) {
        return this.baseMapper.incrCurrentGroupCount(orderId);
    }

    /**
     * 根据订单编号获取订单
     *
     * @param orderSn
     * @return
     */
    @Override
    public Order getByOrderSn(String orderSn) {
        return this.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderSn, orderSn).eq(Order::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
