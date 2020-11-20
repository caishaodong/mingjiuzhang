package com.dong.mingjiuzhang.domain.entity.dto;

import lombok.Data;

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
}
