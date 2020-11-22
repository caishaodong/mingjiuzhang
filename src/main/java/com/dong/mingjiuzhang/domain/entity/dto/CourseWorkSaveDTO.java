package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-22 11:38
 * @Description
 **/
@Data
public class CourseWorkSaveDTO {
    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 作业图片（json）
     */
    private String workImage;

    public void paramCheck() {
        if (Objects.isNull(this.courseId) || StringUtil.isBlank(this.workImage)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
