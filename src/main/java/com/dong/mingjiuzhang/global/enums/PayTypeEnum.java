package com.dong.mingjiuzhang.global.enums;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-24 21:48
 * @Description
 **/
public enum PayTypeEnum {
    COURSE(1, "课程"),
    COURSE_SERIES(2, "系列");

    private Integer type;
    private String desc;

    PayTypeEnum(Integer type, String desc) {
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
     * 根据type获取PayTypeEnum
     *
     * @param type
     * @return
     */
    public static PayTypeEnum getPayTypeEnumByType(Integer type) {
        if (Objects.isNull(type)) {
            return null;
        }

        PayTypeEnum typeEnum = null;
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (Objects.equals(type, payTypeEnum.getType())) {
                typeEnum = payTypeEnum;
                break;
            }
        }
        return typeEnum;
    }
}
