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
 * 活动题目表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_activity_request")
public class ActivityRequest extends Model<ActivityRequest> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 题目生效时间
     */
    private LocalDateTime gmtStartEffect;

    /**
     * 题目失效时间
     */
    private LocalDateTime gmtEndEffect;

    /**
     * 答对题目所得积分
     */
    private Integer integral;

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
