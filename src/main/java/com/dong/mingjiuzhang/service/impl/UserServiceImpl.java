package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.BaseUser;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.dto.RegisterDTO;
import com.dong.mingjiuzhang.domain.entity.dto.UserUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.vo.SysCityVO;
import com.dong.mingjiuzhang.domain.entity.vo.UserApiLoginVO;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.UserTypeEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.encryption.DigestUtil;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.mapper.UserMapper;
import com.dong.mingjiuzhang.service.SysCityService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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

    @Autowired
    private SysCityService sysCityService;

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
     * 根据用户token获取User信息
     *
     * @param request
     * @return
     */
    @Override
    public User getUserByToken(HttpServletRequest request) {
        // 获取用户id
        Long userId = JwtUtil.getUserIdByToken(request.getHeader(JwtUtil.TOKEN_HEADER));
        if (Objects.isNull(userId)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }
        User user = this.getOkById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param registerDTO
     * @return
     */
    @Override
    public UserApiLoginVO register(RegisterDTO registerDTO) {
        // 保存用户信息
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        ReflectUtil.setCreateInfo(user, User.class);
        String salt = DigestUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(DigestUtil.digestString(user.getPassword(), salt));
        // 根据城市编码获取名称
        SysCityVO sysCityVO = sysCityService.getSysCityByAreaCode(registerDTO.getAreaCode());
        user.setProvinceCode(sysCityVO.getProvinceCode());
        user.setProvince(sysCityVO.getProvince());
        user.setCityCode(sysCityVO.getCityCode());
        user.setCity(sysCityVO.getCity());
        user.setAreaCode(sysCityVO.getAreaCode());
        user.setArea(sysCityVO.getArea());
        save(user);

        // 封账返回用户信息
        UserApiLoginVO userApiLoginVo = new UserApiLoginVO();
        BeanUtils.copyProperties(user, userApiLoginVo);

        // 生成token
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(user, baseUser);
        baseUser.setUserTypeEnum(UserTypeEnum.API_USER);
        userApiLoginVo.setToken(JwtUtil.createToken(baseUser));
        return userApiLoginVo;
    }

    /**
     * 登录
     *
     * @param existUser
     */
    @Override
    public UserApiLoginVO login(User existUser) {
        // 封账返回用户信息
        UserApiLoginVO userApiLoginVo = new UserApiLoginVO();
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

    /**
     * 修改用户信息
     *
     * @param userUpdateDTO
     * @param user
     */
    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO, User user) {
        // 修改头像
        if (StringUtil.equals(userUpdateDTO.getType(), "avatar")) {
            user.setAvatar(userUpdateDTO.getAvatar());
        }
        // 修改昵称
        if (StringUtil.equals(userUpdateDTO.getType(), "username")) {
            user.setUsername(userUpdateDTO.getUsername());
        }
        // 修改地区
        if (StringUtil.equals(userUpdateDTO.getType(), "area")) {
            // 根据区县编码获取上机编码
            SysCityVO sysCityVO = sysCityService.getSysCityByAreaCode(userUpdateDTO.getAreaCode());
            user.setProvinceCode(sysCityVO.getProvinceCode());
            user.setProvince(sysCityVO.getProvince());
            user.setCityCode(sysCityVO.getCityCode());
            user.setCity(sysCityVO.getCity());
            user.setAreaCode(sysCityVO.getAreaCode());
            user.setArea(sysCityVO.getArea());
        }
        this.updateById(user);
    }

    /**
     * 用户表用户积分增加
     *
     * @param userId
     * @param addIntegral
     */
    @Override
    public void addIntegral(Long userId, Integer addIntegral) {
        this.baseMapper.addIntegral(userId, addIntegral);
    }

    /**
     * 减去用户积分
     *
     * @param userId
     * @param subtractIntegral
     */

    @Override
    public void subtractIntegral(Long userId, Long subtractIntegral) {
        this.baseMapper.subtractIntegral(userId, subtractIntegral);
    }
}
