package com.dong.mingjiuzhang.domain.entity;

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
 * 答题表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_answer")
public class Answer extends Model<Answer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 学科id
     */
    private Long subjectDictId;

    /**
     * 学科一级类目id
     */
    private Long subjectFirstDictId;

    /**
     * 学科二级类目id
     */
    private Long subjectSecondDictId;

    /**
     * 题目分类id
     */
    private Long subjectCateId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 答题使用时长（秒）
     */
    private Long answerDuration;

    /**
     * 答题状态（1：答题中，2答题完成）
     */
    private Integer status;

    /**
     * 是否删除：0否 1是
     */
    private Integer isDeleted;

    /**
     * 添加时间
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
