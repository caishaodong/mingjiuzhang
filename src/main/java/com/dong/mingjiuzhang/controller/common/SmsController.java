package com.dong.mingjiuzhang.controller.common;

import com.dong.mingjiuzhang.domain.entity.dto.SmsSendDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 短信
 **/
@RestController
@RequestMapping("sms/")
public class SmsController extends BaseController {
    @Autowired
    private SmsService smsService;

    /**
     * 发送短信
     *
     * @param smsSendDTO
     * @return
     */
    @PostMapping("/sendCode")
    public ResponseResult smsSendCode(@RequestBody SmsSendDTO smsSendDTO) {
        // 参数校验
        smsSendDTO.paramCheck();
        // 发送短信
        smsService.smsSendCode(smsSendDTO);
        return success();
    }
}
