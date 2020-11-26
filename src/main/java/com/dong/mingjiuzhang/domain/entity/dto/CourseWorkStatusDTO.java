package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.CourseWorkStatusEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-26 22:28
 * @Description
 **/
@Data
public class CourseWorkStatusDTO {
    /**
     * 作业id
     */
    private Long courseWorkId;
    /**
     * 状态：0待批改 1批改中 2批改完成 3驳回
     */
    private Integer status;
    /**
     * 原因
     */
    private String reason;

    public void paramCheck() {
        if (Objects.isNull(this.courseWorkId) || Objects.isNull(this.status)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 状态校验
        CourseWorkStatusEnum statusEnum = CourseWorkStatusEnum.getCourseWorkStatusEnumByStatus(status);
        if (Objects.isNull(statusEnum)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 驳回原因校验
        if (Objects.equals(statusEnum, CourseWorkStatusEnum.REFUSE_CORRECT) && StringUtil.isBlank(this.reason)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
