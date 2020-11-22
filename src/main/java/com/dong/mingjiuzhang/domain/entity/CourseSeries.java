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
 * 课程系列表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_course_series")
public class CourseSeries extends Model<CourseSeries> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程类目id
     */
    private Long courseCateId;

    /**
     * 课程系列名称
     */
    private String courseSeriesName;

    /**
     * 课程系列价格
     */
    private BigDecimal courseSeriesPrice;

    /**
     * 课程系列拼团价格
     */
    private BigDecimal courseSeriesGroupPrice;

    /**
     * 开始上传时间
     */
    private LocalDateTime gmtStartUpload;

    /**
     * 结束上传时间
     */
    private LocalDateTime gmtEndUpload;

    /**
     * 开始有效开始时间
     */
    private LocalDateTime gmtStartEffect;

    /**
     * 结束有效结束时间
     */
    private LocalDateTime gmtEndEffect;

    /**
     * 课程期类（春季课程，夏季课程，秋季课程）
     */
    private String courseSpecial;

    /**
     * 课程版本（基础课，专题课）
     */
    private String courseEdition;

    /**
     * 反馈方式
     */
    private String feedbackMode;

    /**
     * 内容
     */
    private String content;

    /**
     * 老师id
     */
    private Long teacherId;

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
