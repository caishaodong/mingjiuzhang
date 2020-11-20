package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SysUser;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getOkBySysUserId(Long sysUserId);

    SysUser getOkByMobile(String mobile);

    /**
     * 登录
     *
     * @param existSysUser
     * @return
     */
    String login(SysUser existSysUser);
}
