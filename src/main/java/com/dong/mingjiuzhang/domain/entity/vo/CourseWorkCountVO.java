package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-23 9:44
 * @Description
 **/
@Data
public class CourseWorkCountVO {
    /**
     * 课程类目id
     */
    private Long id;
    /**
     * 课程类目名称
     */
    private String courseCateName;
    /**
     * 待批改的作业数量
     */
    private Long waitCorrectWorkCount;
}
