package com.dong.mingjiuzhang.controller.admin;

import com.dong.mingjiuzhang.domain.entity.SysUser;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordLoginDTO;
import com.dong.mingjiuzhang.domain.entity.dto.SmsLoginDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.config.redis.RedisKey;
import com.dong.mingjiuzhang.global.config.redis.RedisService;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.encryption.DigestUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 管理台用户登入登出
 **/
@RestController
@RequestMapping("admin/")
public class LoginAdminController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisService redisService;

    /**
     * 密码登录
     *
     * @param passwordLoginDTO
     * @return
     */
    @PutMapping("/passwordLogin")
    public ResponseResult<String> passwordLogin(@RequestBody PasswordLoginDTO passwordLoginDTO) {
        // 参数校验
        passwordLoginDTO.paramCheck();
        // 校验用户是否存在
        SysUser existSysUser = sysUserService.getOkByMobile(passwordLoginDTO.getMobile());
        if (Objects.isNull(existSysUser)) {
            throw new BusinessException(BusinessEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
        }
        // 校验密码是否正确
        if (!StringUtil.equals(DigestUtil.digestString(passwordLoginDTO.getPassword(), existSysUser.getSalt()), existSysUser.getPassword())) {
            throw new BusinessException(BusinessEnum.LOGIN_NAME_OR_PASSWORD_ERROR);
        }
        // 登录
        String token = sysUserService.login(existSysUser);
        return success(token);
    }

    /**
     * 短信验证码登录
     *
     * @param smsLoginDTO
     * @return
     */
    @PutMapping("/smsLogin")
    public ResponseResult<String> smsLogin(SmsLoginDTO smsLoginDTO) {
        SysUser existSysUser = sysUserService.getOkByMobile(smsLoginDTO.getMobile());
        if (Objects.isNull(existSysUser)) {
            throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
        }
        // 校验验证码是否正确
        String key = RedisKey.ADMIN_LOGIN_CODE_KEY + existSysUser.getId();
        String smsCode = redisService.getString(key);
        if (StringUtil.equals(smsCode, smsLoginDTO.getCode())) {
            throw new BusinessException(BusinessEnum.SMS_CODE_INVALID);
        }
        // 登录
        String token = sysUserService.login(existSysUser);
        // 删除验证码
        redisService.deleteString(key);
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
