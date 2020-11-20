package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.User;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
public interface UserService extends IService<User> {

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    User getOkById(Long id);

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    User getOkByMobile(String mobile);

}
