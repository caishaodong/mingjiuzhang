package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.PayTypeEnum;
import com.dong.mingjiuzhang.global.enums.SendTypeEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;

import java.util.Objects;

/**
 * @Author caishaodong
 * @Date 2020-11-24 21:03
 * @Description
 **/
@Data
public class OrderSaveDTO {
    /**
     * 取件方式：1自取  2邮寄（必传）
     */
    private Integer sendType;
    /**
     * 收件人姓名（取件方式为邮寄的时候传递）
     */
    private String receiverName;
    /**
     * 收件人电话（取件方式为邮寄的时候传递）
     */
    private String receiverMobile;
    /**
     * 省编码（取件方式为邮寄的时候传递）
     */
    private Integer provinceCode;
    /**
     * 详细地址（取件方式为邮寄的时候传递）
     */
    private String address;
    /**
     * 支付方式：1微信 2支付宝
     */
    private Integer payMethod;
    /**
     * 购买类型：1课程 2：系列（必传）
     */
    private Integer payType;
    /**
     * 课程id（购买类型为课程的时候传递）
     */
    private Long courseId;
    /**
     * 课程系列id（购买类型为系列的时候传递）
     */
    private Long courseSeriesId;
    /**
     * 是否是团购：0否 1是（必传）
     */
    private Integer isGroup;

    public void paramCheck() {
        if (Objects.isNull(this.sendType)) {
            // 取件方式不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        SendTypeEnum sendTypeEnum = SendTypeEnum.getSendTypeEnumByType(this.sendType);
        if (Objects.isNull(sendTypeEnum)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.equals(sendTypeEnum, SendTypeEnum.SELF) && (StringUtil.isBlank(this.receiverName) || StringUtil.isBlank(this.receiverMobile)
                || Objects.isNull(this.provinceCode) || StringUtil.isBlank(this.address))) {
            // 如果是自提，用户信息不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.isNull(this.payType)) {
            // 购买类型不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        PayTypeEnum payTypeEnum = PayTypeEnum.getPayTypeEnumByType(this.payType);
        if (Objects.isNull(payTypeEnum)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.equals(payTypeEnum, PayTypeEnum.COURSE) && Objects.isNull(this.courseId)) {
            // 如果购买的是课程，课程id不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.equals(payTypeEnum, PayTypeEnum.COURSE_SERIES) && Objects.isNull(this.courseSeriesId)) {
            // 如果购买的是系列，系列id不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.isNull(this.isGroup)) {
            // 是否是团购不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        if (Objects.equals(this.isGroup, YesNoEnum.YES.getValue()) && Objects.isNull(this.courseSeriesId)) {
            // 如果是团购，系列id不能为空
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

    }

}
