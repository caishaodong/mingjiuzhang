package com.dong.mingjiuzhang.controller.admin;

import com.dong.mingjiuzhang.domain.entity.dto.PasswordLoginDTO;
import com.dong.mingjiuzhang.domain.entity.dto.SmsLoginDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 系统用户登入登出
 **/
@RestController
@RequestMapping("admin/")
public class LoginAdminController extends BaseController {

    /**
     * 密码登录
     *
     * @param passwordLoginDTO
     * @return
     */
    @PutMapping("/passwordLogin")
    public ResponseResult passwordLogin(@RequestBody PasswordLoginDTO passwordLoginDTO) {
        String token = "";
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
        String token = "";
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
