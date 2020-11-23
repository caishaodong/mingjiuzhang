package com.dong.mingjiuzhang.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 作业表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_course_work")
public class CourseWork extends Model<CourseWork> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 课程类目id
     */
    private Long courseCateId;

    /**
     * 课程系列id
     */
    private Long courseSeriesId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 作业图片（json）
     */
    private String workImage;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 是否完成批改：0否 1是
     */
    private Integer isFinishCorrect;

    /**
     * 是否删除：0否 1是
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
