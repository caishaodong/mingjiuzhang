package com.dong.mingjiuzhang.domain.entity.vo;

import com.dong.mingjiuzhang.domain.entity.SubjectRequest;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-23 12:07
 * @Description
 **/
@Data
public class SubjectRequestVO extends SubjectRequest {
    /**
     * 学科名称
     */
    private String subjectDictName;
    /**
     * 学科一级类目名称
     */
    private String subjectFirstDictName;
    /**
     * 学科二级类目名称
     */
    private String subjectSecondDictName;
    /**
     * 题目分类名称
     */
    private String subjectCateName;
}
