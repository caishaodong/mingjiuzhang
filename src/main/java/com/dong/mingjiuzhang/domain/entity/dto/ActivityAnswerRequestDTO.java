package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-23 15:40
 * @Description
 **/
@Data
public class ActivityAnswerRequestDTO {
    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 题目id
     */
    private Long activityRequestId;
    /**
     * 是否正确：0否 1是
     */
    private Integer isCorrect;

    public void paramCheck() {
        if (Objects.isNull(this.activityId) || Objects.isNull(this.activityRequestId) || Objects.isNull(this.isCorrect)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
