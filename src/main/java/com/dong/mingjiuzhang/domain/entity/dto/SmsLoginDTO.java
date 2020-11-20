package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 短信验证码登录入参
 **/
@Data
public class SmsLoginDTO {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 短信验证码
     */
    private String code;

    /**
     * 参数校验
     */
    public static void paramCheck(SmsLoginDTO smsLoginDTO) {
        if (StringUtil.isBlank(smsLoginDTO.mobile) || StringUtil.isBlank(smsLoginDTO.code)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 手机号格式校验
        if (!StringUtil.isMobile(smsLoginDTO.mobile)) {
            throw new BusinessException(BusinessEnum.MOBILE_FORMAT_ERROR);
        }
    }
}
