package com.dong.mingjiuzhang.global.enums;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:33
 * @Description
 **/
public enum BusinessEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    NO_ACCESS_PERMISSION(300, "您没有访问权限"),
    PARAM_ERROR(400, "参数错误"),
    DATA_ERROR(501, "数据错误"),

    REQUEST_RATE_LIMIT(201, "网络繁忙，请稍后再试"),

    USER_NOT_EXIST(401, "用户不存在"),
    MOBILE_EXIST(402, "该手机号已存在"),
    LOGIN_NAME_OR_PASSWORD_ERROR(403, "用户名或密码错误"),
    LOGIN_NAME_IS_EMPTY(404, "用户名不能为空"),
    PASSWORD_IS_EMPTY(405, "密码不能为空"),
    USER_CANCEL(406, "该账号已注销"),
    USER_FROZEN(407, "该账号已冻结"),
    NOT_LOGIN(408, "请重新登录"),
    MOBILE_FORMAT_ERROR(409, "手机号格式不正确"),
    HAVE_NO_PERMISSION(410, "暂无权限访问该资源"),
    SMS_CODE_INVALID(411, "请重新获取验证码"),
    SMS_CODE_ERROR(412, "验证码错误"),
    NOT_TEACHER(413, "您的身份不是老师"),
    NO_YESTERDAY_REQUEST_ANALYSIS(414, "暂无昨日该年级题目解析"),
    NOT_START_ACTIVITY(415, "活动尚未开始"),
    ALREADY_END_ACTIVITY(416, "活动已经结束"),
    COMMODITY_NOT_ENOUGH(417, "商品库存不足"),
    USER_COMMODITY_NOT_ENOUGH(418, "积分不足"),
    PAY_FAILED(419, "支付失败"),
    ALREADY_GROUPING(420, "您当前已经正在拼团中"),
    GROUPING_FAILED(421, "拼团失败"),
    GROUP_ORDER_EXPIRED(422, "当前拼团已经失效"),
    ;
    private Integer code;
    private String desc;

    BusinessEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
