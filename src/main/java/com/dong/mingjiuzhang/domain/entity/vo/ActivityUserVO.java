package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-23 14:04
 * @Description
 **/
@Data
public class ActivityUserVO {
    /**
     * 用户所得积分
     */
    private Integer totalIntegral;
    /**
     * 答题次数
     */
    private Integer answerCount;
}
