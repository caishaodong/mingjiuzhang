package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.page.PageUtil;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

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

    public void paramCheck() {
        if (StringUtil.isBlank(type)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
