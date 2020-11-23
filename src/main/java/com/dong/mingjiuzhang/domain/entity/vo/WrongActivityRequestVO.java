package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-23 15:11
 * @Description
 **/
@Data
public class WrongActivityRequestVO {
    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 年级（0其他，1一年级，2二年级，3三年级，4四年级，5五年级，6六年级，7七年级，8八年级）
     */
    private String grade;

    /**
     * 题目类型：1选择题  2填空题
     */
    private Integer type;

    /**
     * 题目内容
     */
    private String title;

    /**
     * 题目图片（json）
     */
    private String titleImage;

    /**
     * 题目选项（json）
     */
    private String requestOption;

    /**
     * 答案内容（json）
     */
    private String answer;

    /**
     * 答案图片（json）
     */
    private String answerImage;
}
