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
 * 学科题目表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_subject_request")
public class SubjectRequest extends Model<SubjectRequest> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 题目所属的活动id
     */
    private Long activityId;

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
     * 修改时间
     */
    private LocalDateTime gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
