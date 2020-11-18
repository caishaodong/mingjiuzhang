package com.dong.mingjiuzhang.global;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-08-06 14:27
 * @Description
 **/
@Data
@Builder
@AllArgsConstructor
public class ResponseResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResponseResult success() {
        return new ResponseResult(200, "成功", null);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(200, "成功", data);
    }

    public static ResponseResult error() {
        return new ResponseResult(500, "失败", null);
    }

    public static ResponseResult error(Integer code, String errorMsg) {
        return new ResponseResult(code, errorMsg, null);
    }

    public static ResponseResult error(BusinessEnum businessEnum) {
        return new ResponseResult(businessEnum.getCode(), businessEnum.getDesc(), null);
    }

    public static ResponseResult error(BindingResult bindingResult) {
        if (Objects.nonNull(bindingResult) && bindingResult.hasErrors()) {
            return new ResponseResult(BusinessEnum.PARAM_ERROR.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            return error();
        }
    }
}
