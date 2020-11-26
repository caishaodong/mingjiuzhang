package com.dong.mingjiuzhang.global.util.pay;

import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.PayMethodEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-25 22:03
 * @Description
 **/
public class OrderPayUtil {
    /**
     * 支付
     *
     * @param orderPayDTO
     * @return
     */
    public static OrderPayResult pay(OrderPayDTO orderPayDTO) {
        OrderPayResult orderPayResult = null;
        if (Objects.equals(orderPayDTO.getPayMethod(), PayMethodEnum.WECHAT.getPayMethod())) {
            // 微信支付
            orderPayResult = null;
        } else if (Objects.equals(orderPayDTO.getPayMethod(), PayMethodEnum.ALIPAY.getPayMethod())) {
            // 支付宝支付
            orderPayResult = null;
        } else {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        return orderPayResult;
    }

    /**
     * 支付回调
     *
     * @return
     */
    public static OrderPayResult payBack() {
        OrderPayResult orderPayResult = null;
        return orderPayResult;
    }
}
