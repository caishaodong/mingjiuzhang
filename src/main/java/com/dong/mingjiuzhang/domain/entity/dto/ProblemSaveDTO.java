package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

/**
 * 问题表
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Data
public class ProblemSaveDTO {

    /**
     * 标题
     */
    private String problemTitle;

    /**
     * 问题内容
     */
    private String problemContent;

    /**
     * 问题图片（json）
     */
    private String problemImage;

    public void paramCheck() {
        if (StringUtil.isBlank(this.problemTitle) || StringUtil.isBlank(this.problemContent)
                || StringUtil.isBlank(this.problemImage)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
