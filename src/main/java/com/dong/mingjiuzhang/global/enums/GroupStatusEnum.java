package com.dong.mingjiuzhang.global.enums;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-25 20:52
 * @Description
 **/
public enum GroupStatusEnum {

    GROUPING(0, "拼团中"),
    GROUP_SUCCESS(1, "拼团成功"),
    GROUP_FAIL(2, "拼团失败");

    private Integer status;
    private String desc;

    GroupStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据状态获取枚举
     *
     * @param status
     * @return
     */
    public static GroupStatusEnum getGroupStatusEnumByStatus(Integer status) {
        if (Objects.isNull(status)) {
            return null;
        }
        GroupStatusEnum groupStatusEnum = null;
        for (GroupStatusEnum value : GroupStatusEnum.values()) {
            if (Objects.equals(status, value.getStatus())) {
                groupStatusEnum = value;
                break;
            }
        }
        return groupStatusEnum;
    }
}
