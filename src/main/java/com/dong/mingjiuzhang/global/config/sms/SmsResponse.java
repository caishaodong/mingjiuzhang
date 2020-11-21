package com.dong.mingjiuzhang.global.config.sms;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-21 14:59
 * @Description
 **/
@Data
public class SmsResponse {
    /**
     * 请求id，批量发送，只会产生一个请求id
     */
    private String RequestId;
    /**
     * 发送流水号
     */
    private String SerialNo;
    /**
     * 手机号码
     */
    private String PhoneNumber;
    /**
     * 计费条数
     */
    private Integer Fee;
    /**
     * 用户Session内容
     */
    private String SessionContext;
    /**
     * 短信请求错误码
     */
    private String Code;
    /**
     * 短信请求错误码描述
     */
    private String Message;
    /**
     * 国家码或地区码，例如CN,US等，对于未识别出国家码或者地区码，默认返回DEF
     */
    private String IsoCode;
}
