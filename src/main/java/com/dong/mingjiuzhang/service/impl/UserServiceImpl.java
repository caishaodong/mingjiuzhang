package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.BaseUser;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.dto.RegisterDTO;
import com.dong.mingjiuzhang.domain.entity.vo.UserApiLoginVo;
import com.dong.mingjiuzhang.global.enums.UserTypeEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.util.encryption.DigestUtil;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.UserMapper;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.BeanUtils;
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

    /**
     * 用户注册
     *
     * @param registerDTO
     * @return
     */
    @Override
    public UserApiLoginVo register(RegisterDTO registerDTO) {
        // 保存用户信息
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        ReflectUtil.setCreateInfo(user, User.class);
        String salt = DigestUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(DigestUtil.digestString(user.getPassword(), salt));
        save(user);

        // 封账返回用户信息
        UserApiLoginVo userApiLoginVo = new UserApiLoginVo();
        BeanUtils.copyProperties(user, userApiLoginVo);

        // 生成token
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(user, baseUser);
        userApiLoginVo.setToken(JwtUtil.createToken(baseUser));
        return userApiLoginVo;
    }

    /**
     * 密码登录
     *
     * @param existUser
     */
    @Override
    public UserApiLoginVo login(User existUser) {
        // 封账返回用户信息
        UserApiLoginVo userApiLoginVo = new UserApiLoginVo();
        BeanUtils.copyProperties(existUser, userApiLoginVo);
        // 生成token
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(existUser, baseUser);
        baseUser.setUserTypeEnum(UserTypeEnum.API_USER);
        String token = JwtUtil.createToken(baseUser);
        userApiLoginVo.setToken(token);
        return userApiLoginVo;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param passwordUpdateDTO
     */
    @Override
    public void updatePassword(Long userId, PasswordUpdateDTO passwordUpdateDTO) {
        String salt = DigestUtil.generateSalt();
        User user = new User();
        user.setId(userId);
        user.setSalt(salt);
        user.setPassword(DigestUtil.digestString(passwordUpdateDTO.getNewPassword(), salt));
        this.updateById(user);
    }
}
