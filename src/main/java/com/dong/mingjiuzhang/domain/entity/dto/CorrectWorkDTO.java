package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-23 10:27
 * @Description 批改作业
 **/
@Data
public class CorrectWorkDTO {
    /**
     * 作业id
     */
    private Long courseWorkId;
    /**
     * 作业图片（json）
     */
    private String workImage;

    public void paramCheck() {
        if (Objects.isNull(this.courseWorkId) || StringUtil.isBlank(workImage)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
