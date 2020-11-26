package com.dong.mingjiuzhang.global.util.pay;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author caishaodong
 * @Date 2020-11-26 14:08
 * @Description
 **/
@Data
public class OrderPayBackDTO {
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 三方订单编号
     */
    private String thirdOrderSn;
    /**
     * 支付方式：1微信 2支付宝
     */
    private Integer payMethod;
    /**
     * 支付状态：0未支付 1已支付 2支付失败
     */
    private Integer payStatus;
    /**
     * 实付金额
     */
    private BigDecimal realPrice;
    /**
     * 是否是团购：0否 1是
     */
    private Integer isGroup;
    /**
     * 团购订单编号
     */
    private String groupOrderSn;
}
