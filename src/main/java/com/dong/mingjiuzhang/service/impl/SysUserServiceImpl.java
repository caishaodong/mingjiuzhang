package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.BaseUser;
import com.dong.mingjiuzhang.domain.entity.SysUser;
import com.dong.mingjiuzhang.global.enums.UserTypeEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import com.dong.mingjiuzhang.mapper.SysUserMapper;
import com.dong.mingjiuzhang.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getOkBySysUserId(Long sysUserId) {
        return getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, sysUserId).eq(SysUser::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    @Override
    public SysUser getOkByMobile(String mobile) {
        return getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile, mobile).eq(SysUser::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 登录
     *
     * @param existSysUser
     * @return
     */
    @Override
    public String login(SysUser existSysUser) {
        // 生成token
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(existSysUser, baseUser);
        baseUser.setUserTypeEnum(UserTypeEnum.ADMIN_USER);
        String token = JwtUtil.createToken(baseUser);
        return token;
    }
}
