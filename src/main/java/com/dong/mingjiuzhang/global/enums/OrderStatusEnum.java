package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-11-25 20:21
 * @Description
 **/
public enum OrderStatusEnum {
    PROCESSING(0, "处理中"),
    TO_BE_DELIVERED(1, "待发货"),
    DELIVERED(2, "已发货"),
    FINISH(3, "已完成"),
    INVALID(4, "已失效");

    private Integer status;
    private String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
