package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordUpdateDTO;
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
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
@Controller
@RequestMapping("api/user")
public class UserController extends BaseController {
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
        // 获取用户id
        Long userId = JwtUtil.getUserIdByToken(request.getHeader(JwtUtil.TOKEN_HEADER));
        if (Objects.isNull(userId)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }
        // 获取短信验证码
        String msgCode = redisService.getString(RedisKey.API_PASSWORD_CODE_KEY + userId);
        if (StringUtil.isBlank(msgCode)) {
            throw new BusinessException(BusinessEnum.SMS_CODE_INVALID);
        }
        if (!StringUtil.equals(msgCode, passwordUpdateDTO.getCode())) {
            throw new BusinessException(BusinessEnum.SMS_CODE_ERROR);
        }
        // 修改密码
        userService.updatePassword(userId, passwordUpdateDTO);
        return success();
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/geInfo")
    public ResponseResult<User> updatePassword(HttpServletRequest request) {
        // 获取用户id
        Long userId = JwtUtil.getUserIdByToken(request.getHeader(JwtUtil.TOKEN_HEADER));
        if (Objects.isNull(userId)) {
            throw new BusinessException(BusinessEnum.NOT_LOGIN);
        }
        User user = userService.getOkById(userId);
        if (Objects.isNull(user)) {
            throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
        }
        return success(user);
    }

}
