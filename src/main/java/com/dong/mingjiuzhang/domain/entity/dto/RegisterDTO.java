package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.UserIdentityEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description C端用户登入登出
 **/
@Data
public class RegisterDTO {
    /**
     * 用户身份：老师，学生
     */
    private String identity;
    /**
     * 昵称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 年级（0其他，1一年级，2二年级，3三年级，4四年级，5五年级，6六年级，7七年级，8八年级）
     */
    private String grade;
    /**
     * 省编码
     */
    private Integer provinceCode;
    /**
     * 市编码
     */
    private Integer cityCode;
    /**
     * 区编码
     */
    private Integer areaCode;

    public void paramCheck() {
        if (StringUtil.isBlank(this.username) || StringUtil.isBlank(this.password) || StringUtil.isBlank(this.mobile) || StringUtil.isBlank(this.grade)
                || Objects.isNull(this.provinceCode) || Objects.isNull(this.cityCode) || Objects.isNull(this.areaCode)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 用户手机号校验
        if (!StringUtil.isMobile(this.mobile)) {
            throw new BusinessException(BusinessEnum.MOBILE_FORMAT_ERROR);
        }
        // 用户身份校验
        if (StringUtil.isBlank(this.identity)) {
            this.setIdentity(UserIdentityEnum.STUDENT.getIdentity());
        } else if (Objects.isNull(UserIdentityEnum.getUserIdentityEnumByIdentity(this.getIdentity()))) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
