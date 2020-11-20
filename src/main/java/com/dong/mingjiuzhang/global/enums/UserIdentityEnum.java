package com.dong.mingjiuzhang.global.enums;

import com.dong.mingjiuzhang.global.util.string.StringUtil;

/**
 * @Author caishaodong
 * @Date 2020-11-18 22:58
 * @Description 用户身份枚举
 **/
public enum UserIdentityEnum {

    TEACHER("teacher", "老师"),
    STUDENT("student", "学生");

    private String identity;
    private String desc;

    UserIdentityEnum(String identity, String desc) {
        this.identity = identity;
        this.desc = desc;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据identity获取身份枚举
     *
     * @param identity
     * @return
     */
    public static UserIdentityEnum getUserIdentityEnumByIdentity(String identity) {
        if (StringUtil.isBlank(identity)) {
            return null;
        }
        UserIdentityEnum userIdentityEnum = null;
        for (UserIdentityEnum identityEnum : UserIdentityEnum.values()) {
            if (StringUtil.equals(identityEnum.getIdentity(), identity)) {
                userIdentityEnum = identityEnum;
                break;
            }
        }
        return userIdentityEnum;
    }
}
