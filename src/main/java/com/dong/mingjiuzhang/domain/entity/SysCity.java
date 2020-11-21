package com.dong.mingjiuzhang.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中国省市区地名表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_city")
public class SysCity extends Model<SysCity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 区域编码
     */
    private Integer code;

    /**
     * 上级区域编码
     */
    private Integer parentCode;

    /**
     * 地区名称
     */
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
