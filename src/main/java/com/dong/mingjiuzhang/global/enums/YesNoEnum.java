package com.dong.mingjiuzhang.global.enums;

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
}

