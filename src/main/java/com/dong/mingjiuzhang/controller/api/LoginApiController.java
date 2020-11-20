package com.dong.mingjiuzhang.controller.api;

import com.dong.mingjiuzhang.domain.entity.BaseUser;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.PasswordLoginDTO;
import com.dong.mingjiuzhang.domain.entity.dto.RegisterDTO;
import com.dong.mingjiuzhang.domain.entity.dto.SmsLoginDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.encryption.DigestUtil;
import com.dong.mingjiuzhang.global.util.jwt.JwtUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 用户注册
     *
     * @param registerDTO
     * @return
     */
    @RequestMapping("/register")
    public ResponseResult register(@RequestBody RegisterDTO registerDTO) {
        // 参数校验
        registerDTO.paramCheck();
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
    @RequestMapping("/passwordLogin")
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
        // 生成token
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(existUser, baseUser);
        String token = JwtUtil.createToken(baseUser);
        return success(token);
    }

    /**
     * 短信验证码登录
     *
     * @param smsLoginDTO
     * @return
     */
    @RequestMapping("/smsLogin")
    public ResponseResult smsLogin(SmsLoginDTO smsLoginDTO) {
        String token = "";
        return success(token);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping("/logout")
    public ResponseResult logout() {
        return success();
    }
}
