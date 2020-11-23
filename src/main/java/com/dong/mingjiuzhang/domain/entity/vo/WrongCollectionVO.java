package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author caishaodong
 * @Date 2020-11-23 13:37
 * @Description
 **/
@Data
public class WrongCollectionVO {
    /**
     * 答题记录id
     */
    private Long id;
    /**
     * 学科名称
     */
    private String subjectDictName;
    /**
     * 学科以一级分类名称
     */
    private String subjectFirstDictName;
    /**
     * 学科二级分类名称
     */
    private String subjectSecondDictName;
    /**
     * 学科分类名称
     */
    private String subjectCateName;
    /**
     * 收录时间
     */
    private LocalDateTime gmtCreate;
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
