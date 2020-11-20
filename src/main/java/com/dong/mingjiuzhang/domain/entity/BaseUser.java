package com.dong.mingjiuzhang.domain.entity;

import com.dong.mingjiuzhang.global.enums.UserTypeEnum;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-18 22:58
 * @Description
 **/
@Data
public class BaseUser {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户类型（api：普通用户，admin：系统用户）
     */
    private UserTypeEnum userTypeEnum;
}
