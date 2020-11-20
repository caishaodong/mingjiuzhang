package com.dong.mingjiuzhang.global.enums;

import com.dong.mingjiuzhang.global.util.string.StringUtil;

/**
 * @Author caishaodong
 * @Date 2020-11-18 22:58
 * @Description 短信类型枚举
 **/
public enum MsgTypeEnum {
    REGISTER("register", "注册"),
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

    /**
     * 根据类型获取短信类型枚举
     *
     * @param type
     * @return
     */
    public static MsgTypeEnum getMsgTypeEnumByType(String type) {
        if (StringUtil.isBlank(type)) {
            return null;
        }
        MsgTypeEnum msgTypeEnum = null;
        for (MsgTypeEnum typeEnum : MsgTypeEnum.values()) {
            if (StringUtil.equals(typeEnum.getType(), type)) {
                msgTypeEnum = typeEnum;
                break;
            }
        }
        return msgTypeEnum;
    }
}
