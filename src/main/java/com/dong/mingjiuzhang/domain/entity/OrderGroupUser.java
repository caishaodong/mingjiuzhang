package com.dong.mingjiuzhang.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 拼团订单用户表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order_group_user")
public class OrderGroupUser extends Model<OrderGroupUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String groupOrderSn;

    /**
     * 三方订单编号
     */
    private String thirdOrderSn;

    /**
     * 拼团用户id
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
     * 支付状态（0未支付，1已支付，2支付失败）
     */
    private Integer payStatus;

    /**
     * 状态：1待发货 2已发货 3已完成 4已失效 5已退款
     */
    private Integer status;

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
    private Integer provinceCode;

    /**
     * 省
     */
    private String province;

    /**
     * 应付金额
     */
    private BigDecimal shouldPrice;

    /**
     * 实付金额
     */
    private BigDecimal realPrice;

    /**
     * 订单失效时间
     */
    private LocalDateTime gmtOrderExpiration;

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
