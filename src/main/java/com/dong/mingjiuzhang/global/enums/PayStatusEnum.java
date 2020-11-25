package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-11-25 22:13
 * @Description
 **/
public enum PayStatusEnum {
    PROCESSING(0, "处理中"),
    PAY_SUCCESS(1, "支付成功"),
    PAY_FAILED(2, "支付失败");

    private Integer payStatus;
    private String desc;

    PayStatusEnum(Integer payStatus, String desc) {
        this.payStatus = payStatus;
        this.desc = desc;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
