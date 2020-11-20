package com.dong.mingjiuzhang.service.impl;

import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.domain.entity.dto.SmsSendDTO;
import com.dong.mingjiuzhang.global.config.redis.RedisKey;
import com.dong.mingjiuzhang.global.config.redis.RedisService;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.MsgTypeEnum;
import com.dong.mingjiuzhang.global.enums.UserTypeEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.sms.SmsUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import com.dong.mingjiuzhang.service.SmsService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-09-18 15:16
 * @Description 短信服务
 **/
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 发送短信
     *
     * @param smsSendDTO
     */
    @Override
    public void smsSendCode(SmsSendDTO smsSendDTO) {
        if (StringUtil.equals(smsSendDTO.getUserType(), UserTypeEnum.API_USER.getType())) {
            // C端用户
            apiSmsSend(smsSendDTO);
        } else if (StringUtil.equals(smsSendDTO.getUserType(), UserTypeEnum.ADMIN_USER.getType())) {
            // 系统用户
            adminSmsSend(smsSendDTO);
        }
    }

    /**
     * C端用户发送短信
     *
     * @param smsSendDTO
     */
    public void apiSmsSend(SmsSendDTO smsSendDTO) {
        // 获取用户信息
        User existUser = userService.getOkByMobile(smsSendDTO.getMobile());
        // 短信内容
        String content = "";
        // 获取短信验证码
        String smsCode = StringUtil.getSmsCode();
        // 缓存key
        String cacheKey = "";
        if (StringUtil.equals(smsSendDTO.getMsgType(), MsgTypeEnum.REGISTER.getType())) {
            // 注册
            if (Objects.nonNull(existUser)) {
                throw new BusinessException(BusinessEnum.MOBILE_EXIST);
            }
            // 获取注册短信模板
            content = "注册短信验证码：%s";
            content = String.format(content, smsCode);
            cacheKey = RedisKey.API_REGISTER_CODE_KEY + smsSendDTO.getMobile();
        } else if (StringUtil.equals(smsSendDTO.getMsgType(), MsgTypeEnum.LOGIN.getType())) {
            // 登录
            if (Objects.isNull(existUser)) {
                throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
            }
            // 获取登录短信模板
            content = "登录短信验证码：%s";
            content = String.format(content, smsCode);
            cacheKey = RedisKey.API_LOGIN_CODE_KEY + smsSendDTO.getMobile();
        } else if (StringUtil.equals(smsSendDTO.getMsgType(), MsgTypeEnum.PASSWORD.getType())) {
            // 修改密码
            if (Objects.isNull(existUser)) {
                throw new BusinessException(BusinessEnum.USER_NOT_EXIST);
            }
            // 获取修改密码短信模板
            content = "修改短信验证码：%s";
            content = String.format(content, smsCode);
            cacheKey = RedisKey.API_PASSWORD_CODE_KEY + smsSendDTO.getMobile();
        } else {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 发送短信
        SmsUtil.sendMessage(smsSendDTO.getMobile(), content);
        // 缓存短信验证码
        redisService.setString(cacheKey, smsCode, 5 * 60);
    }

    /**
     * 系统用户发送短信
     *
     * @param smsSendDTO
     */
    public void adminSmsSend(SmsSendDTO smsSendDTO) {
        if (StringUtil.equals(smsSendDTO.getMsgType(), MsgTypeEnum.REGISTER.getType())) {
            // 注册
        } else if (StringUtil.equals(smsSendDTO.getMsgType(), MsgTypeEnum.LOGIN.getType())) {
            // 登录
        } else if (StringUtil.equals(smsSendDTO.getMsgType(), MsgTypeEnum.PASSWORD.getType())) {
            // 修改密码
        } else {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
