package com.dong.mingjiuzhang.global.exception;

import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:39
 * @Description
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseResult businessException(BusinessException e) {
        return ResponseResult.error(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException e, BindingResult bindingResult) {
        String errorMessage = "";
        if (Objects.nonNull(bindingResult) && bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                LOGGER.error(objectError.getDefaultMessage());
                errorMessage = StringUtil.isNotBlank(errorMessage) ? errorMessage : objectError.getDefaultMessage();
            }
        }
        return ResponseResult.error(BusinessEnum.PARAM_ERROR.getCode(), errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult illegalArgumentException(IllegalArgumentException e) {
        return ResponseResult.error(BusinessEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseResult.error(BusinessEnum.FAIL);
    }
}
