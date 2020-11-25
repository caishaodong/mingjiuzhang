package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-11-25 20:52
 * @Description
 **/
public enum GroupStatusEnum {

    GROUPING(0, "拼团中"),
    GROUP_SUCCESS(1, "拼团成功"),
    GROUP_FAIL(2, "拼团失败");

    private Integer status;
    private String desc;

    GroupStatusEnum(Integer status, String desc) {
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
