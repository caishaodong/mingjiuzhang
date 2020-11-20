package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.UserMapper;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    public User getOkById(Long id) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getId, id).eq(User::getIsDeleted, YesNoEnum.NO.getValue()));
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public User getOkByMobile(String mobile) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile).eq(User::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
