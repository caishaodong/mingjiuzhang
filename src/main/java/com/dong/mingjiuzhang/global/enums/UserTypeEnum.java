package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-11-18 22:58
 * @Description 用户类型枚举
 **/
public enum UserTypeEnum {
    API_USER("api", "C端用户"),
    ADMIN_USER("admin", "系统用户");

    private String type;
    private String desc;

    UserTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
