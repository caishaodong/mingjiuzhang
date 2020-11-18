package com.dong.mingjiuzhang.global.exception;


import com.dong.mingjiuzhang.global.enums.BusinessEnum;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:41
 * @Description
 **/
public class BusinessException extends RuntimeException {
    private Integer errorCode;
    private String errorMsg;

    public BusinessException() {
        super();
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(BusinessEnum businessEnum) {
        super();
        this.errorCode = businessEnum.getCode();
        this.errorMsg = businessEnum.getDesc();
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
