package com.dong.mingjiuzhang.service;

import com.dong.mingjiuzhang.domain.entity.dto.SmsSendDTO;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 短信服务
 **/
public interface SmsService {
    /**
     * 发送短信
     *
     * @param smsSendDTO
     */
    void smsSendCode(SmsSendDTO smsSendDTO);
}
