package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-09-18 14:53
 * @Description
 **/
public enum RoleEnum {
    SUPER_ADMIN("superAdmin", "超级管理员"),
    ADMIN("admin", "管理员"),
    OTHER("other", "其它");

    private String code;
    private String desc;

    RoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
