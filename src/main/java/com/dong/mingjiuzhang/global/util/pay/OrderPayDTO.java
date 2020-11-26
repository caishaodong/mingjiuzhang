package com.dong.mingjiuzhang.global.util.pay;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author caishaodong
 * @Date 2020-11-26 13:53
 * @Description
 **/
@Data
public class OrderPayDTO {
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 支付方式：1微信 2支付宝
     */
    private Integer payMethod;
    /**
     * 应付金额
     */
    private BigDecimal shouldPrice;
    /**
     * 是否是团购：0否 1是
     */
    private Integer isGroup;
    /**
     * 添加时间
     */
    private LocalDateTime gmtCreate;
}
