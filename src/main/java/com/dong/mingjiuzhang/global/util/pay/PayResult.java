package com.dong.mingjiuzhang.global.util.pay;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-25 21:51
 * @Description
 **/
@Data
public class PayResult {
    /**
     * 是否支付成功
     */
    private Boolean success;
    /**
     * 错误编码
     */
    private String errorCode;
    /**
     * 错误描述
     */
    private String errorDesc;
}
