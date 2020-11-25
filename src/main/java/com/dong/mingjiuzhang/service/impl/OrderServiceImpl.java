package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.domain.entity.OrderGroupUser;
import com.dong.mingjiuzhang.domain.entity.dto.PreOrderDTO;
import com.dong.mingjiuzhang.domain.entity.vo.OrderSaveVO;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.PayStatusEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.util.pay.PayResult;
import com.dong.mingjiuzhang.global.util.pay.PayUtil;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.OrderMapper;
import com.dong.mingjiuzhang.service.OrderGroupUserService;
import com.dong.mingjiuzhang.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderGroupUserService orderGroupUserService;

    /**
     * 保存订单
     *
     * @param preOrderDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSaveVO saveOrder(PreOrderDTO preOrderDTO) {
        // 保存订单
        Order order = new Order();
        order.setOrderSn(UUID.randomUUID().toString().replaceAll("-", ""));
        BeanUtils.copyProperties(preOrderDTO, order);
        ReflectUtil.setCreateInfo(order, Order.class);
        this.save(order);

        if (Objects.equals(order.getGroupStatus(), YesNoEnum.YES.getValue())) {
            // 如果是拼团订单，保存拼团用户信息
            OrderGroupUser orderGroupUser = new OrderGroupUser();
            orderGroupUser.setOrderId(order.getId());
            orderGroupUser.setUserId(order.getUserId());
            orderGroupUser.setUserGrade(order.getUserGrade());
            orderGroupUser.setSendType(order.getSendType());
            orderGroupUser.setPayStatus(YesNoEnum.NO.getValue());
            orderGroupUser.setReceiverName(order.getReceiverName());
            orderGroupUser.setReceiverMobile(order.getReceiverMobile());
            orderGroupUser.setProvinceCode(order.getProvinceCode());
            orderGroupUser.setProvince(order.getProvince());
            orderGroupUser.setShouldPrice(order.getShouldPrice());
            orderGroupUser.setGmtOrderExpiration(order.getGmtOrderExpiration());
            ReflectUtil.setCreateInfo(orderGroupUser, OrderGroupUser.class);
            orderGroupUserService.save(orderGroupUser);
        }

        // 调用第三方支付
        PayResult payResult = PayUtil.pay(order);

        // 支付结果校验
        OrderSaveVO orderSaveVO = new OrderSaveVO();
        if (Objects.isNull(payResult) || !payResult.getSuccess()) {
            String errorCode = Objects.isNull(payResult) ? String.valueOf(BusinessEnum.PAY_FAILED.getCode()) : payResult.getErrorCode();
            String errorDesc = Objects.isNull(payResult) ? BusinessEnum.PAY_FAILED.getDesc() : payResult.getErrorDesc();
            LOGGER.info("支付失败，错误码：" + errorCode + "，错误描述：" + errorDesc);

            // 返回支付失败信息
            orderSaveVO.setShouldPrice(order.getShouldPrice());
            orderSaveVO.setPayStatus(PayStatusEnum.PAY_FAILED.getPayStatus());
            orderSaveVO.setGmtCreate(order.getGmtCreate());
            orderSaveVO.setDesc(errorDesc);
        } else {
            // 返回支付成功信息
            orderSaveVO.setShouldPrice(order.getShouldPrice());
            orderSaveVO.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
            orderSaveVO.setGmtCreate(order.getGmtCreate());
        }
        return orderSaveVO;
    }
}
