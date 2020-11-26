package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.CourseWorkSearchTypeEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.page.PageUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-22 22:12
 * @Description
 **/
@Data
public class CourseWorkSearchDTO extends PageUtil {
    /**
     * 类型：本周week，历史history，上传记录all
     */
    private String type;

    /**
     * 本周一日期
     */
    @Ignore
    private String thisMonday;

    public void paramCheck() {
        if (StringUtil.isBlank(type)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        CourseWorkSearchTypeEnum searchTypeEnum = CourseWorkSearchTypeEnum.getCourseWorkSearchTypeEnumByType(this.type);
        if (Objects.isNull(searchTypeEnum)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
