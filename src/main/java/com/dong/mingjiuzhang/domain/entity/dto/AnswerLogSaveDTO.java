package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-23 13:03
 * @Description
 **/
@Data
public class AnswerLogSaveDTO {
    /**
     * 答题ID
     */
    private Long answerId;
    /**
     * 学科题目id
     */
    private Long subjectRequestId;
    /**
     * 是否正确：0否 1是
     */
    private Integer isCorrect;
    /**
     * 是否加入错题集：0否 1是
     */
    private Integer isWrongCollection;

    /**
     * 是否完成答题：0否 1是
     */
    private Integer isFinishAnswer;
    /**
     * 答题使用时长（秒）
     */
    private Long answerDuration;

    public void paramCheck() {
        if (Objects.isNull(this.answerId) || Objects.isNull(this.subjectRequestId) || Objects.isNull(this.isCorrect) || Objects.isNull(this.isFinishAnswer)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.equals(this.isCorrect, YesNoEnum.NO.getValue()) && Objects.isNull(this.isWrongCollection)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.equals(this.isFinishAnswer, YesNoEnum.YES.getValue()) && Objects.isNull(this.answerDuration)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
