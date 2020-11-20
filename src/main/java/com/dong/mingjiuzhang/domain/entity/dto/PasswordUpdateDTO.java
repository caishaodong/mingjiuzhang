package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 修改密码入参
 **/
@Data
public class PasswordUpdateDTO {
    /**
     * 短信验证码
     */
    private String code;
    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 参数校验
     */
    public void paramCheck() {
        if (StringUtil.isBlank(this.code) || StringUtil.isBlank(newPassword)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
