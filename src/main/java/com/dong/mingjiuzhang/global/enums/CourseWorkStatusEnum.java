package com.dong.mingjiuzhang.global.enums;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-26 22:08
 * @Description
 **/
public enum CourseWorkStatusEnum {
    WAITING_CORRECT(0, "待批改"),
    CORRECTING(1, "批改中"),
    FINISH_CORRECT(2, "批改完成"),
    REFUSE_CORRECT(3, "驳回");

    private Integer status;
    private String desc;

    CourseWorkStatusEnum(Integer status, String desc) {
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
    public static CourseWorkStatusEnum getCourseWorkStatusEnumByStatus(Integer status) {
        if (Objects.isNull(status)) {
            return null;
        }
        CourseWorkStatusEnum courseWorkStatusEnum = null;
        for (CourseWorkStatusEnum value : CourseWorkStatusEnum.values()) {
            if (Objects.equals(status, value.getStatus())) {
                courseWorkStatusEnum = value;
                break;
            }
        }
        return courseWorkStatusEnum;
    }
}
