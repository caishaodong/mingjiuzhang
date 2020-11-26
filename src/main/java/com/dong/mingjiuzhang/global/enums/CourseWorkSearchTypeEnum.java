package com.dong.mingjiuzhang.global.enums;

import com.dong.mingjiuzhang.global.util.string.StringUtil;

/**
 * @Author caishaodong
 * @Date 2020-11-26 22:34
 * @Description
 **/
public enum CourseWorkSearchTypeEnum {
    WEEK("week", "本周"),
    HISTORY("history", "历史"),
    ALL("all", "上传记录");

    private String type;
    private String desc;

    CourseWorkSearchTypeEnum(String type, String desc) {
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
     * 根据类型获取类型枚举
     *
     * @param type
     * @return
     */
    public static CourseWorkSearchTypeEnum getCourseWorkSearchTypeEnumByType(String type) {
        if (StringUtil.isBlank(type)) {
            return null;
        }
        CourseWorkSearchTypeEnum courseWorkSearchTypeEnum = null;
        for (CourseWorkSearchTypeEnum value : CourseWorkSearchTypeEnum.values()) {
            if (StringUtil.equals(value.getType(), type)) {
                courseWorkSearchTypeEnum = value;
                break;
            }
        }
        return courseWorkSearchTypeEnum;
    }
}
