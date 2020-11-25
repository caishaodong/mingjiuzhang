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
public class PayUtil {
    /**
     * 支付
     *
     * @param order
     * @return
     */
    public static PayResult pay(Order order) {
        PayResult payResult = null;
        if (Objects.equals(order.getPayMethod(), PayMethodEnum.WECHAT.getPayMethod())) {
            // 微信支付
            payResult = null;
        } else if (Objects.equals(order.getPayMethod(), PayMethodEnum.ALIPAY.getPayMethod())) {
            // 支付宝支付
            payResult = null;
        } else {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        return payResult;
    }

    /**
     * 支付回调
     *
     * @return
     */
    public static PayResult payBack() {
        PayResult payResult = null;
        return payResult;
    }
}
