package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.MsgTypeEnum;
import com.dong.mingjiuzhang.global.enums.UserTypeEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 发送短信
 **/
@Data
public class SmsSendDTO {
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 短信类型（login:登录，password:修改密码）
     */
    private String msgType;
    /**
     * 用户类型（api:C端用户，admin：系统用户）
     */
    private String userType;

    public void paramCheck() {
        if (StringUtil.isBlank(this.mobile) || StringUtil.isBlank(this.msgType) || StringUtil.isBlank(userType)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 手机号格式校验
        if (!StringUtil.isMobile(this.mobile)) {
            throw new BusinessException(BusinessEnum.MOBILE_FORMAT_ERROR);
        }
        // 短信类型校验
        if (Objects.isNull(MsgTypeEnum.getMsgTypeEnumByType(this.msgType))) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 用户类型校验
        if (Objects.isNull(UserTypeEnum.getUserTypeEnumByType(this.userType))) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
