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
 * 问题回复表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_problem_reply")
public class ProblemReply extends Model<ProblemReply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问题ID
     */
    private Long problemId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 回复内容
     */
    private String problemReplyContent;

    /**
     * 回复内容图片（json）
     */
    private String problemReplyImage;

    /**
     * 问题状态 0：不显示 1：显示
     */
    private Integer problemReplyStatus;

    /**
     * 是否删除  0否  1是
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
