package com.dong.mingjiuzhang.global.enums;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-24 21:13
 * @Description
 **/
public enum SendTypeEnum {
    SELF(1, "自提"),
    POST(2, "邮寄");

    private Integer type;
    private String desc;

    SendTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据type获取SendTypeEnum
     *
     * @param type
     * @return
     */
    public static SendTypeEnum getSendTypeEnumByType(Integer type) {
        if (Objects.isNull(type)) {
            return null;
        }

        SendTypeEnum typeEnum = null;
        for (SendTypeEnum sendTypeEnum : SendTypeEnum.values()) {
            if (Objects.equals(type, sendTypeEnum.getType())) {
                typeEnum = sendTypeEnum;
                break;
            }
        }
        return typeEnum;
    }
}
