package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordUpdateDTO;
import com.dong.mingjiuzhang.domain.entity.dto.UpdateUserDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.config.redis.RedisKey;
import com.dong.mingjiuzhang.global.config.redis.RedisService;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description API用户管理
 **/
@Controller
@RequestMapping("api/user")
public class UserApiController extends BaseController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    /**
     * 修改密码
     *
     * @param passwordUpdateDTO
     * @return
     */
    @PutMapping("/password")
    public ResponseResult updatePassword(HttpServletRequest request, @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        // 参数校验
        passwordUpdateDTO.paramCheck();
        User user = getUserByToken(request);

        // 校验短信验证码
        String msgCode = redisService.getString(RedisKey.API_PASSWORD_CODE_KEY + user.getId());
        if (StringUtil.isBlank(msgCode)) {
            throw new BusinessException(BusinessEnum.SMS_CODE_INVALID);
        }
        if (!StringUtil.equals(msgCode, passwordUpdateDTO.getCode())) {
            throw new BusinessException(BusinessEnum.SMS_CODE_ERROR);
        }
        // 修改密码
        userService.updatePassword(user.getId(), passwordUpdateDTO);
        return success();
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getInfo")
    public ResponseResult<User> updatePassword(HttpServletRequest request) {
        User user = getUserByToken(request);
        return success(user);
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @param updateUserDTO
     * @return
     */
    @PutMapping("/update")
    public ResponseResult updateAvatar(HttpServletRequest request, @RequestBody UpdateUserDTO updateUserDTO) {
        // 参数校验
        updateUserDTO.paramCheck();
        User user = getUserByToken(request);

        // 修改用户信息
        userService.updateUser(updateUserDTO, user);
        return success();
    }

    /**
     * 根据用户token获取User信息
     *
     * @param request
     * @return
     */
    public User getUserByToken(HttpServletRequest request) {
        // 获取用户id
        Long userId = JwtUtil.getUserIdByToken(request.getHeader(JwtUtil.TOKEN_HEADER));
        if (Objects.isNull(userId)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }
        User user = userService.getOkById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
        }
        return user;
    }


}
