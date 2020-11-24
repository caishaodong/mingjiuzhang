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
 * 订单表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 年级（0其他，1一年级，2二年级，3三年级，4四年级，5五年级，6六年级，7七年级，8八年级）
     */
    private String userGrade;

    /**
     * 取件方式：1自取  2邮寄
     */
    private Integer sendType;

    /**
     * 收件人姓名
     */
    private String receiverName;

    /**
     * 收件人电话
     */
    private String receiverMobile;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 省
     */
    private Integer province;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 支付状态：0未支付 1已支付
     */
    private Integer payStatus;

    /**
     * 状态：0处理中 1待发货 2已发货 3已完成 4已失效
     */
    private Integer status;

    /**
     * 应付金额
     */
    private BigDecimal shouldPrice;

    /**
     * 实付金额
     */
    private BigDecimal realPrice;

    /**
     * 购买类型：1课程 2系列
     */
    private Integer payType;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程系列id
     */
    private Long courseSeriesId;

    /**
     * 课程系列名称
     */
    private String courseSeriesName;

    /**
     * 课程系列老师id
     */
    private Long teacherId;

    /**
     * 课程系列老师名称
     */
    private String teacherName;

    /**
     * 课程系列老师手机号
     */
    private String teacherMobile;

    /**
     * 订单失效时间
     */
    private LocalDateTime gmtOrderExpiration;

    /**
     * 是否是团购：0否 1是
     */
    private Integer isGroup;

    /**
     * 拼团需要的订单数量
     */
    private Integer groupCount;

    /**
     * 当前拼团人数
     */
    private Integer currentGroupCount;

    /**
     * 拼团状态：0拼团中 1拼团成功 2拼团失败
     */
    private Integer groupStatus;

    /**
     * 拼团完成时间
     */
    private LocalDateTime gmtGroupFinish;

    /**
     * 拼团失效时间
     */
    private LocalDateTime gmtGroupExpiration;

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
