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
 * 用户信息表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 证件ID
     */
    private String cardId;

    /**
     * 图像url
     */
    private String userAvatar;

    /**
     * 学号
     */
    private String studyNumber;

    /**
     * 年级
     */
    private String grade;

    /**
     * 性别 1:男,2:女
     */
    private Boolean sex;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 用户历史总积分
     */
    private Long totalIntegral;

    /**
     * 用户当前积分
     */
    private Long integral;

    /**
     * 是否是会员：0不是  1是
     */
    private Boolean isMember;

    /**
     * 商户地址
     */
    private String userImage;

    /**
     * 是否删除：0否 1是
     */
    private Boolean isDeleted;

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
