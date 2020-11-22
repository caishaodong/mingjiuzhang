package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.dto.RegisterDTO;
import com.dong.mingjiuzhang.domain.entity.dto.UpdateUserDTO;
import com.dong.mingjiuzhang.domain.entity.vo.UserApiLoginVo;

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

    /**
     * 用户注册
     *
     * @param registerDTO
     * @return
     */
    UserApiLoginVo register(RegisterDTO registerDTO);

    /**
     * 密码登录
     *
     * @param existUser
     */
    UserApiLoginVo login(User existUser);

    /**
     * 修改密码
     *
     * @param userId
     * @param passwordUpdateDTO
     */
    void updatePassword(Long userId, PasswordUpdateDTO passwordUpdateDTO);

    /**
     * 修改用户信息
     *
     * @param user
     */
    void updateUser(UpdateUserDTO updateUserDTO, User user);
}
