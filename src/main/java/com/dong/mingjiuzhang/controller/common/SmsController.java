package com.dong.mingjiuzhang.controller.common;

import com.dong.mingjiuzhang.domain.entity.dto.SmsSendDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
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

    /**
     * 发送短信
     *
     * @param smsSendDTO
     * @return
     */
    @RequestMapping("/send")
    public ResponseResult smsSend(@RequestBody SmsSendDTO smsSendDTO) {
        // 发送短信
        return success();
    }
}
