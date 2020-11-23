package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-23 12:48
 * @Description
 **/
@Data
public class AnswerSaveDTO {
    /**
     * 题目分类id
     */
    private Long subjectCateId;
    /**
     * 活动id
     */
    private Long activityId;

    public void paramCheck() {
        if (Objects.isNull(subjectCateId) && Objects.isNull(activityId)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
