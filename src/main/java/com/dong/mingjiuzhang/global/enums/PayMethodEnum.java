package com.dong.mingjiuzhang.global.enums;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-25 21:43
 * @Description
 **/
public enum PayMethodEnum {
    WECHAT(1, "微信支付"),
    ALIPAY(2, "支付宝支付");

    private Integer payMethod;
    private String desc;

    PayMethodEnum(Integer payMethod, String desc) {
        this.payMethod = payMethod;
        this.desc = desc;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取支付方式枚举
     *
     * @param payMethod
     * @return
     */
    public static PayMethodEnum getPayMethodEnum(Integer payMethod) {
        if (Objects.isNull(payMethod)) {
            return null;
        }
        PayMethodEnum payMethodEnum = null;
        for (PayMethodEnum value : PayMethodEnum.values()) {
            if (Objects.equals(value.getPayMethod(), payMethod)) {
                payMethodEnum = value;
                break;
            }
        }
        return payMethodEnum;
    }
}
