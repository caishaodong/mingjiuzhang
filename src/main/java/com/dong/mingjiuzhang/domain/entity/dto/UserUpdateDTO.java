package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-22 10:26
 * @Description 更改用户头像入参
 **/
@Data
public class UserUpdateDTO {
    /**
     * 修改类型（avatar修改头像，username修改昵称，area修改地区）（必选，其他参数根据类型选择）
     */
    private String type;
    /**
     * 用户头像地址
     */
    private String avatar;
    /**
     * 昵称
     */
    private String username;
    /**
     * 区县编码（修改地区，只需要传区县编码，不需要传省和城市编码）
     */
    private Integer areaCode;

    public void paramCheck() {
        if (StringUtil.isBlank(this.type)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 修改头像
        if (StringUtil.equals(this.type, "avatar") && StringUtil.isBlank(this.avatar)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 修改昵称
        if (StringUtil.equals(this.type, "username") && StringUtil.isBlank(this.username)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 修改地区
        if (StringUtil.equals(this.type, "area") && Objects.isNull(this.areaCode)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
