package com.dong.mingjiuzhang.global.util.sms;

import com.dong.mingjiuzhang.global.util.string.StringUtil;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 短信工具类
 **/
public class SmsUtil {

    /**
     * 发送短信
     *
     * @param mobile
     * @param content
     */
    public static void sendMessage(String mobile, String content) {
        if (!StringUtil.isMobile(mobile) || StringUtil.isBlank(content)) {
            return;
        }
        // 发送短信
    }
}
