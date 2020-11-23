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
 * 学科二级类目表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_subject_second_dict")
public class SubjectSecondDict extends Model<SubjectSecondDict> {

    private static final long serialVersionUID = 1L;

    /**
     * 学科二级类目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学科二级类目名称
     */
    private String subjectSecondDictName;

    /**
     * 学科类目id
     */
    private Long subjectDictId;

    /**
     * 学科一级类目ID
     */
    private Long subjectFirstDictId;

    /**
     * 排序
     */
    private Integer sort;

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
