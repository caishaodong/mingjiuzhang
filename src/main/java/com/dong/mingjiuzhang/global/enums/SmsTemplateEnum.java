package com.dong.mingjiuzhang.global.enums;

import com.dong.mingjiuzhang.global.util.string.StringUtil;

/**
 * @Author caishaodong
 * @Date 2020-11-21 11:25
 * @Description
 **/
public enum SmsTemplateEnum {
    REGISTER("register", "778486", "【明九章】您的验证码：%s，请在3分钟内使用，如非本人操作请忽略。"),
    LOGIN("login", "778486", "【明九章】您的验证码：%s，请在3分钟内使用，如非本人操作请忽略。"),
    PASSWORD("password", "778486", "【明九章】您的验证码：%s，请在3分钟内使用，如非本人操作请忽略。"),
    ORDER("order", "778505", "【明九章】您已购买了%s，可以正常使用该课程。"),
    ;

    private String code;
    private String templateID;
    private String template;

    SmsTemplateEnum(String code, String templateID, String template) {
        this.code = code;
        this.templateID = templateID;
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * 根据code获取短信模板配置
     *
     * @param code
     * @return
     */
    public static SmsTemplateEnum getSmsTemplateEnumByCode(String code) {
        if (StringUtil.isBlank(code)) {
            return null;
        }
        SmsTemplateEnum smsTemplateEnum = null;
        for (SmsTemplateEnum templateEnum : SmsTemplateEnum.values()) {
            if (StringUtil.equals(code, templateEnum.getCode())) {
                smsTemplateEnum = templateEnum;
                break;
            }
        }
        return smsTemplateEnum;
    }
}
