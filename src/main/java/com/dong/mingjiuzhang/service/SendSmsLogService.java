package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SendSmsLog;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-21
 */
public interface SendSmsLogService extends IService<SendSmsLog> {

    /**
     * 保存短信发送记录
     *
     * @param responseResult
     */
    void save(SendSmsResponse sendSmsResponse);
}
