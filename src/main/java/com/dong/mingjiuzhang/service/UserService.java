package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.dto.RegisterDTO;
import com.dong.mingjiuzhang.domain.entity.dto.UserUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.vo.UserApiLoginVO;

import javax.servlet.http.HttpServletRequest;

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
     * 根据token获取用户信息
     *
     * @param request
     * @return
     */
    User getUserByToken(HttpServletRequest request);

    /**
     * 用户注册
     *
     * @param registerDTO
     * @return
     */
    UserApiLoginVO register(RegisterDTO registerDTO);

    /**
     * 密码登录
     *
     * @param existUser
     */
    UserApiLoginVO login(User existUser);

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
    void updateUser(UserUpdateDTO userUpdateDTO, User user);

    /**
     * 用户表用户积分增加
     *
     * @param userId
     * @param addIntegral
     */
    void addIntegral(Long userId, Integer addIntegral);

    /**
     * 减去用户积分
     *
     * @param userId
     * @param commodityIntegral
     */
    void subtractIntegral(Long userId, Long subtractIntegral);
}
