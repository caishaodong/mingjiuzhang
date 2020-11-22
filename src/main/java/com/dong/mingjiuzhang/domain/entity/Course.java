package com.dong.mingjiuzhang.domain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_course")
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程类目id
     */
    private Long courseCateId;

    /**
     * 课程系列id
     */
    private Long courseSeriesId;

    /**
     * 是否付费 0：否 是：1
     */
    private Integer isPayment;

    /**
     * 课程价格
     */
    private BigDecimal coursePrice;

    /**
     * 视频url
     */
    private String videoUrl;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程结束展示时间
     */
    private LocalDateTime gmtStartShow;

    /**
     * 课程开始展示时间
     */
    private LocalDateTime gmtEndShow;

    /**
     * 简介
     */
    private String synopsis;

    /**
     * 排序
     */
    private Integer sort;

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
