package com.dong.mingjiuzhang.domain.entity.vo;

import lombok.Data;

/**
 * @Author caishaodong
 * @Date 2020-11-23 10:03
 * @Description
 **/
@Data
public class CourseWorkVO {
    /**
     * 作业id
     */
    private Long id;
    /**
     * 作业图片
     */
    private String workImage;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 课程分类名称
     */
    private String courseCateName;
    /**
     * 系列名称
     */
    private String courseSeriesName;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 上传时间
     */
    private String gmtCreate;
    /**
     * 状态：0待批改 1批改中 2批改完成 3驳回
     */
    private Integer status;
    /**
     * 原因
     */
    private String reason;
}
