package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-08-06 18:15
 * @Description
 **/
public enum UserStatusEnum {
    NORMAL(0, "正常"),
    CANCEL(1, "注销"),
    FROZEN(2, "冻结");

    private int status;
    private String desc;

    UserStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
