package com.dong.mingjiuzhang.global.util.pay;

import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.domain.entity.OrderGroupUser;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PreOrderDTO;
import com.dong.mingjiuzhang.domain.entity.vo.OrderSaveVO;
import com.dong.mingjiuzhang.global.config.redis.RedisService;
import com.dong.mingjiuzhang.global.constant.OrderConstants;
import com.dong.mingjiuzhang.global.enums.*;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.service.OrderGroupUserService;
import com.dong.mingjiuzhang.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-26 14:00
 * @Description
 **/
@Component
public class OrderPayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPayService.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGroupUserService orderGroupUserService;

    /**
     * 保存订单
     *
     * @param preOrderDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderSaveVO genOrder(PreOrderDTO preOrderDTO) {
        Order order = null;
        if (Objects.equals(preOrderDTO.getIsGroup(), YesNoEnum.NO.getValue())) {
            // 如果不是拼团
            // 保存订单
            order = saveOrder(preOrderDTO);

            // 设置支付订单redis
            redisService.setLong(order.getOrderSn(), OrderConstants.ORDER_EFFECTIVE_SECONDS, OrderConstants.ORDER_EFFECTIVE_SECONDS);
        } else {
            // 如果是拼团
            Order groupOrder = preOrderDTO.getGroupOrder();
            if (Objects.isNull(groupOrder)) {
                // 如果是发起拼团
                // 保存订单
                order = saveOrder(preOrderDTO);
                // 保存拼团发起人用户拼团订单
                OrderGroupUser orderGroupUser = saveMasterOrderGroupUser(order, preOrderDTO);
                // 设置拼团订单redis
                redisService.setLong(order.getOrderSn(), OrderConstants.GROUP_ORDER_EFFECTIVE_SECONDS, OrderConstants.GROUP_ORDER_EFFECTIVE_SECONDS);
                // 设置支付订单redis
                redisService.setLong(orderGroupUser.getGroupOrderSn(), OrderConstants.ORDER_EFFECTIVE_SECONDS, OrderConstants.ORDER_EFFECTIVE_SECONDS);
            } else {
                // 如果是参加别人的拼团
                order = groupOrder;
                // 拼团人数加1
                int count = orderService.incrCurrentGroupCount(order.getId());
                if (count <= 0) {
                    throw new BusinessException(BusinessEnum.GROUPING_FAILED);
                }
                // 保存拼团参与者用户拼团订单
                OrderGroupUser orderGroupUser = saveSlaveOrderGroupUser(preOrderDTO);
                // 设置支付订单redis
                redisService.setLong(orderGroupUser.getGroupOrderSn(), OrderConstants.ORDER_EFFECTIVE_SECONDS, OrderConstants.ORDER_EFFECTIVE_SECONDS);
            }
        }

        // 去支付
        OrderPayDTO orderPayDTO = new OrderPayDTO();
        OrderSaveVO orderSaveVO = toPay(orderPayDTO);
        return orderSaveVO;
    }

    /**
     * 保存订单
     *
     * @param preOrderDTO
     * @return
     */
    private Order saveOrder(PreOrderDTO preOrderDTO) {
        Order order = new Order();
        order.setOrderSn(orderService.getOrderSn());
        BeanUtils.copyProperties(preOrderDTO, order);
        ReflectUtil.setCreateInfo(order, Order.class);
        orderService.save(order);
        return order;
    }

    /**
     * 保存拼团发起人用户拼团订单
     */
    private OrderGroupUser saveMasterOrderGroupUser(Order order, PreOrderDTO preOrderDTO) {
        // 如果是拼团订单，保存拼团用户信息
        OrderGroupUser orderGroupUser = new OrderGroupUser();
        orderGroupUser.setOrderId(order.getId());
        orderGroupUser.setGroupOrderSn(orderService.getGroupOrderSn());
        orderGroupUser.setUserId(order.getUserId());
        orderGroupUser.setUserGrade(order.getUserGrade());
        orderGroupUser.setSendType(order.getSendType());
        orderGroupUser.setPayStatus(YesNoEnum.NO.getValue());
        orderGroupUser.setReceiverName(order.getReceiverName());
        orderGroupUser.setReceiverMobile(order.getReceiverMobile());
        orderGroupUser.setProvinceCode(order.getProvinceCode());
        orderGroupUser.setProvince(order.getProvince());
        orderGroupUser.setShouldPrice(order.getShouldPrice());
        orderGroupUser.setGmtOrderExpiration(preOrderDTO.getGmtGroupOrderExpiration());
        ReflectUtil.setCreateInfo(orderGroupUser, OrderGroupUser.class);
        orderGroupUserService.save(orderGroupUser);
        return orderGroupUser;
    }

    /**
     * 保存参与者拼团信息
     *
     * @param preOrderDTO
     */
    private OrderGroupUser saveSlaveOrderGroupUser(PreOrderDTO preOrderDTO) {
        Order order = preOrderDTO.getGroupOrder();
        User slaveUser = preOrderDTO.getSlaveUser();
        OrderGroupUser orderGroupUser = new OrderGroupUser();
        orderGroupUser.setOrderId(order.getId());
        orderGroupUser.setGroupOrderSn(orderService.getGroupOrderSn());
        orderGroupUser.setUserId(slaveUser.getId());
        orderGroupUser.setUserGrade(slaveUser.getGrade());
        orderGroupUser.setSendType(preOrderDTO.getSendType());
        orderGroupUser.setPayStatus(YesNoEnum.NO.getValue());
        orderGroupUser.setReceiverName(preOrderDTO.getReceiverName());
        orderGroupUser.setReceiverMobile(preOrderDTO.getReceiverMobile());
        orderGroupUser.setProvinceCode(preOrderDTO.getProvinceCode());
        orderGroupUser.setProvince(preOrderDTO.getProvince());
        orderGroupUser.setShouldPrice(preOrderDTO.getShouldPrice());
        orderGroupUser.setGmtOrderExpiration(preOrderDTO.getGmtGroupOrderExpiration());
        ReflectUtil.setCreateInfo(orderGroupUser, OrderGroupUser.class);
        orderGroupUserService.save(orderGroupUser);
        return orderGroupUser;
    }

    /**
     * 去支付
     *
     * @param orderPayDTO
     * @return
     */
    private OrderSaveVO toPay(OrderPayDTO orderPayDTO) {
        // 调用第三方支付
        OrderPayResult orderPayResult = OrderPayUtil.pay(orderPayDTO);
        // 支付结果校验
        OrderSaveVO orderSaveVO = new OrderSaveVO();
        if (Objects.isNull(orderPayResult) || !orderPayResult.getSuccess()) {
            String errorCode = Objects.isNull(orderPayResult) ? String.valueOf(BusinessEnum.PAY_FAILED.getCode()) : orderPayResult.getErrorCode();
            String errorDesc = Objects.isNull(orderPayResult) ? BusinessEnum.PAY_FAILED.getDesc() : orderPayResult.getErrorDesc();
            LOGGER.info("支付失败，错误码：{}，错误描述：{}", errorCode, errorDesc);

            // 返回支付失败信息
            orderSaveVO.setShouldPrice(orderPayDTO.getShouldPrice());
            orderSaveVO.setPayStatus(PayStatusEnum.PAY_FAILED.getPayStatus());
            orderSaveVO.setGmtCreate(orderPayDTO.getGmtCreate());
            orderSaveVO.setDesc(errorDesc);
        } else {
            // 返回支付成功信息
            orderSaveVO.setShouldPrice(orderPayDTO.getShouldPrice());
            orderSaveVO.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
            orderSaveVO.setGmtCreate(orderPayDTO.getGmtCreate());
        }
        return orderSaveVO;
    }

    /**
     * 支付回调
     *
     * @param orderPayBackDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void payBack(OrderPayBackDTO orderPayBackDTO) {
        if (Objects.isNull(orderPayBackDTO)) {
            return;
        }
        String orderSn = orderPayBackDTO.getOrderSn();
        Integer payStatus = orderPayBackDTO.getPayStatus();
        Integer isGroup = orderPayBackDTO.getIsGroup();
        String groupOrderSn = orderPayBackDTO.getGroupOrderSn();
        LOGGER.info("订单号:{}，支付状态：{}，是否团购：{}，团购订单号：{}", orderSn, payStatus, isGroup, groupOrderSn);
        if (!Objects.equals(payStatus, PayStatusEnum.PAY_SUCCESS.getPayStatus())) {
            // 支付失败，返回
            return;
        }
        // 校验订单是否存在
        Order order = orderService.getByOrderSn(orderSn);
        if (Objects.isNull(order)) {
            // 订单不存在，返回
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        // 支付成功
        if (Objects.equals(isGroup, YesNoEnum.NO.getValue())) {
            // 如果不是团购
            // 修改订单
            order.setThirdOrderSn(orderPayBackDTO.getThirdOrderSn());
            order.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
            order.setStatus(Objects.equals(order.getSendType(), SendTypeEnum.SELF.getType()) ?
                    OrderStatusEnum.FINISH.getStatus() : OrderStatusEnum.TO_BE_DELIVERED.getStatus());
            order.setTotalPrice(orderPayBackDTO.getRealPrice());
            order.setRealPrice(orderPayBackDTO.getRealPrice());
            order.setGmtModified(now);
            orderService.updateById(order);
        } else {
            // 如果是团购
            // 校验团购订单是否存在
            OrderGroupUser orderGroupUser = orderGroupUserService.getByOrderIdAndGroupOrderSn(order.getId(), groupOrderSn);
            if (Objects.isNull(orderGroupUser)) {
                // 团购订单不存在，返回
                return;
            }
            // 修改团购订单信息
            orderGroupUser.setThirdOrderSn(orderPayBackDTO.getThirdOrderSn());
            orderGroupUser.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
            orderGroupUser.setStatus(Objects.equals(orderGroupUser.getSendType(), SendTypeEnum.SELF.getType()) ?
                    OrderStatusEnum.FINISH.getStatus() : OrderStatusEnum.TO_BE_DELIVERED.getStatus());
            orderGroupUser.setRealPrice(orderPayBackDTO.getRealPrice());
            orderGroupUser.setGmtModified(now);
            orderGroupUserService.updateById(orderGroupUser);
            // 修改订单信息
            order.setTotalPrice(order.getTotalPrice().add(orderPayBackDTO.getRealPrice()));
            if (order.getCurrentGroupCount() >= order.getGroupCount()) {
                // 拼团人数足够，拼团完成
                order.setGroupStatus(GroupStatusEnum.GROUP_SUCCESS.getStatus());
                order.setGmtGroupFinish(now);
            }
            orderService.updateById(order);
        }
    }

    /**
     * 订单超时处理
     *
     * @param orderSn 订单编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void orderPayTimeout(String orderSn) {
        // 校验订单是否存在
        Order order = orderService.getByOrderSn(orderSn);
        if (Objects.isNull(order)) {
            return;
        }
        // 校验订单状态
        if (Objects.equals(order.getIsGroup(), YesNoEnum.NO.getValue())) {
            // 不是拼团订单
            if (!Objects.equals(order.getPayStatus(), PayStatusEnum.PROCESSING.getPayStatus())) {
                return;
            }
            // 未支付，则设置订单状态为超时失效
            order.setStatus(OrderStatusEnum.INVALID.getStatus());
            orderService.updateById(order);
        } else {
            // 是拼团订单
            if (!Objects.equals(order.getGroupStatus(), GroupStatusEnum.GROUPING.getStatus())) {
                return;
            }
            // 拼团中，设置为拼团失败
            order.setGroupStatus(GroupStatusEnum.GROUP_FAIL.getStatus());
            orderService.updateById(order);
            // 已经拼团人员退款操作 // TODO
            orderGroupRefund(order);
        }
    }


    /**
     * 拼团订单超时处理
     *
     * @param groupOrderSn 拼团订单编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void groupOrderPayTimeout(String groupOrderSn) {
        LocalDateTime now = LocalDateTime.now();
        // 校验订单是否存在
        OrderGroupUser orderGroupUser = orderGroupUserService.getByGroupOrderSn(groupOrderSn);
        if (Objects.isNull(orderGroupUser)) {
            return;
        }
        Long orderId = orderGroupUser.getOrderId();
        Order order = orderService.getOkById(orderId);
        if (Objects.isNull(order)) {
            return;
        }
        if (!Objects.equals(orderGroupUser.getPayStatus(), PayStatusEnum.PROCESSING.getPayStatus())) {
            // 校验订单状态
            return;
        }

        // 未支付，订单设置为超时
        orderGroupUser.setStatus(OrderStatusEnum.INVALID.getStatus());
        orderGroupUser.setGmtOrderExpiration(now);
        orderGroupUserService.updateById(orderGroupUser);
        // 原订单拼团人数减一
        order.setCurrentGroupCount(order.getCurrentGroupCount() - 1);
        // 如果拼团人数只有1的话，则拼团设置为失败
        Integer currentGroupCount = order.getCurrentGroupCount();
        if (currentGroupCount.intValue() == 1) {
            order.setGroupStatus(GroupStatusEnum.GROUP_FAIL.getStatus());
        }
        orderService.updateById(order);
    }

    /**
     * 拼团订单超时，给已经支付的用户退款
     *
     * @param order
     */
    private void orderGroupRefund(Order order) {
    }
}
