package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-11-18 22:58
 * @Description 短信类型枚举
 **/
public enum MsgTypeEnum {
    LOGIN("login", "登录"),
    PASSWORD("password", "修改密码");

    private String type;
    private String desc;

    MsgTypeEnum(String type, String desc) {
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
