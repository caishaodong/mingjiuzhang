package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author caishaodong
 * @Date 2020-11-25 22:00
 * @Description
 **/
@Data
public class OrderSaveVO {
    /**
     * 支付金额
     */
    private BigDecimal shouldPrice;
    /**
     * 支付状态：0未支付 1已支付 2支付失败
     */
    private Integer payStatus;
    /**
     * 支付时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 描述
     */
    private String desc;

}
