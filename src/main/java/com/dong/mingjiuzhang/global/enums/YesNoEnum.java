package com.dong.mingjiuzhang.global.enums;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-08-06 17:01
 * @Description
 **/
public enum YesNoEnum {
    YES(1),
    NO(0);

    private Integer value;

    YesNoEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static YesNoEnum getYesNoEnumByValue(Integer value) {
        if (Objects.isNull(value)) {
            return null;
        }
        YesNoEnum yesNoEnum = null;
        for (YesNoEnum yesNo : YesNoEnum.values()) {
            if (Objects.equals(yesNo.getValue(), value)) {
                yesNoEnum = yesNo;
                break;
            }
        }
        return yesNoEnum;
    }
}

