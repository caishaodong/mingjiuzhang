package com.dong.mingjiuzhang.global.config.sms;

import com.dong.mingjiuzhang.global.enums.SmsTemplateEnum;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.SendSmsLogService;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 短信工具类
 **/
@Component
public class SmsService {
    @Autowired
    private SendSmsLogService sendSmsLogService;

    /**
     * 发送短信
     *
     * @param mobile
     * @param smsTemplateEnum
     */
    @Async
    public void sendMessage(String mobile, SmsTemplateEnum smsTemplateEnum, String... templateParams) {
        if (!StringUtil.isMobile(mobile) || Objects.isNull(smsTemplateEnum)) {
            return;
        }
        // 发送短信
        SendSmsResponse sendSmsResponse = TencentSmsUtil.sendSms(mobile, smsTemplateEnum, "", templateParams);
        // 保存短信记录
        sendSmsLogService.save(sendSmsResponse);
    }
}
