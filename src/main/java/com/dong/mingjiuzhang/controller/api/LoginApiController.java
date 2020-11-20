package com.dong.mingjiuzhang.controller.api;

import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordLoginDTO;
import com.dong.mingjiuzhang.domain.entity.dto.RegisterDTO;
import com.dong.mingjiuzhang.domain.entity.dto.SmsLoginDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.config.redis.RedisKey;
import com.dong.mingjiuzhang.global.config.redis.RedisService;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.encryption.DigestUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description C端用户登入登出
 **/
@RestController
@RequestMapping("api/")
public class LoginApiController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 用户注册
     *
     * @param registerDTO
     * @return
     */
    @PutMapping("/register")
    public ResponseResult register(@RequestBody RegisterDTO registerDTO) {
        // 参数校验
        registerDTO.paramCheck();
        // 校验用户是否存在
        User existUser = userService.getOkByMobile(registerDTO.getMobile());
        if (Objects.nonNull(existUser)) {
            throw new BusinessException(BusinessEnum.MOBILE_EXIST);
        }
        // 用户注册
        String token = userService.register(registerDTO);
        return success(token);
    }

    /**
     * 密码登录
     *
     * @param passwordLoginDTO
     * @return
     */
    @PutMapping("/passwordLogin")
    public ResponseResult passwordLogin(@RequestBody PasswordLoginDTO passwordLoginDTO) {
        // 参数校验
        passwordLoginDTO.paramCheck();
        // 校验用户是否存在
        User existUser = userService.getOkByMobile(passwordLoginDTO.getMobile());
        if (Objects.isNull(existUser)) {
            throw new BusinessException(BusinessEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
        }
        // 校验密码是否正确
        if (StringUtil.equals(DigestUtil.digestString(passwordLoginDTO.getPassword(), existUser.getSalt()), existUser.getMobile())) {
            throw new BusinessException(BusinessEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
        }
        // 登录
        String token = userService.login(existUser);
        return success(token);
    }

    /**
     * 短信验证码登录
     *
     * @param smsLoginDTO
     * @return
     */
    @PutMapping("/smsLogin")
    public ResponseResult smsLogin(SmsLoginDTO smsLoginDTO) {
        User existUser = userService.getOkByMobile(smsLoginDTO.getMobile());
        if (Objects.isNull(existUser)) {
            throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
        }
        // 校验验证码是否正确
        String smsCode = redisService.getString(RedisKey.API_LOGIN_CODE_KEY + smsLoginDTO.getMobile());
        if (StringUtil.equals(smsCode, smsLoginDTO.getCode())) {
            throw new BusinessException(BusinessEnum.SMS_CODE_INVALID);
        }
        // 登录
        String token = userService.login(existUser);
        return success(token);
    }

    /**
     * 登出
     *
     * @return
     */
    @PutMapping("/logout")
    public ResponseResult logout() {
        return success();
    }
}
