package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 密码登录入参
 **/
@Data
public class PasswordLoginDTO {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;

    /**
     * 参数校验
     */
    public static void paramCheck(PasswordLoginDTO passwordLoginDTO) {
        if (StringUtil.isBlank(passwordLoginDTO.mobile) || StringUtil.isBlank(passwordLoginDTO.password)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (StringUtil.isMobile(passwordLoginDTO.mobile)) {
            throw new BusinessException(BusinessEnum.MOBILE_FORMAT_ERROR);
        }
    }
}
