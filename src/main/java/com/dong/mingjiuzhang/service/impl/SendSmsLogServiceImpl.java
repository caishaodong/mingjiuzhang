package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SendSmsLog;
import com.dong.mingjiuzhang.global.config.sms.TencentSmsUtil;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.mapper.SendSmsLogMapper;
import com.dong.mingjiuzhang.service.SendSmsLogService;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-21
 */
@Service
public class SendSmsLogServiceImpl extends ServiceImpl<SendSmsLogMapper, SendSmsLog> implements SendSmsLogService {

    /**
     * 保存短信发送记录
     *
     * @param sendSmsResponse
     */
    @Override
    public void save(SendSmsResponse sendSmsResponse) {
        if (Objects.isNull(sendSmsResponse)) {
            return;
        }
        String requestId = sendSmsResponse.getRequestId();
        SendStatus[] sendStatusSet = sendSmsResponse.getSendStatusSet();

        List<SendSmsLog> list = new ArrayList<>();
        // 封装发送记录
        for (SendStatus sendStatus : sendStatusSet) {
            SendSmsLog sendSmsLog = new SendSmsLog();
            sendSmsLog.setRequestId(requestId);
            sendSmsLog.setSerialNo(sendStatus.getSerialNo());
            sendSmsLog.setMobile(getSimpleMobile(sendStatus.getPhoneNumber()));
            sendSmsLog.setFee(sendStatus.getFee());
            sendSmsLog.setSessionContext(sendStatus.getSessionContext());
            sendSmsLog.setCode(sendStatus.getCode());
            sendSmsLog.setMessage(sendStatus.getMessage());
            sendSmsLog.setIsSuccess(StringUtil.equals(sendStatus.getCode(), "Ok") ? YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue());
            ReflectUtil.setCreateInfo(sendSmsLog, SendSmsLog.class);

            list.add(sendSmsLog);
        }

        if (!CollectionUtils.isEmpty(list)) {
            this.saveBatch(list);
        }
    }

    /**
     * 获取简单格式手机号
     *
     * @param phoneNumber
     * @return
     */
    public static String getSimpleMobile(String phoneNumber) {
        if (StringUtil.isBlank(phoneNumber)) {
            return phoneNumber;
        }
        return phoneNumber.startsWith(TencentSmsUtil.CN_CODE) ? phoneNumber.substring(TencentSmsUtil.CN_CODE.length()) : phoneNumber;
    }
}
