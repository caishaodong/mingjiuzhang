package com.dong.mingjiuzhang.domain.entity.vo;

import com.dong.mingjiuzhang.domain.entity.CourseWork;
import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-26 15:59
 * @Description
 **/
@Data
public class CourseWorkPageVO extends CourseWork {
    /**
     * 课程名称
     */
    private String courseName;
}
