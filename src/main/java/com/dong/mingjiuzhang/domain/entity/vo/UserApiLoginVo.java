package com.dong.mingjiuzhang.domain.entity.vo;

import com.dong.mingjiuzhang.domain.entity.User;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-21 18:15
 * @Description
 **/
@Data
public class UserApiLoginVo extends User {
    /**
     * 用户token
     */
    private String token;
}
