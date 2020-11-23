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
 * 学科题目分类表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_subject_cate")
public class SubjectCate extends Model<SubjectCate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学科二级类目id
     */
    private Long subjectSecondDictId;

    /**
     * 上级id
     */
    private Long pid;

    /**
     * 题目分类名称
     */
    private String subjectCateName;

    /**
     * 是否是叶子节点：0否 1是
     */
    private Integer isLeaf;

    /**
     * 是否删除：0否 1是
     */
    private Integer isDeleted;

    /**
     * 排序
     */
    private Integer sort;

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
