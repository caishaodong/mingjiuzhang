package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-24 20:45
 * @Description 回复
 **/
@Data
public class ProblemReplyDTO {
    /**
     * 问题ID
     */
    private Long problemId;
    /**
     * 回复内容
     */
    private String problemReplyContent;
    /**
     * 回复内容图片（json）
     */
    private String problemReplyImage;

    public void paramCheck() {
        if (Objects.isNull(this.problemId) || StringUtil.isBlank(this.problemReplyContent) || StringUtil.isBlank(this.problemReplyImage)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }
}
