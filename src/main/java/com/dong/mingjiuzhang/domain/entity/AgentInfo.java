package com.dong.mingjiuzhang.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.string.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 代理信息表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_agent_info")
public class AgentInfo extends Model<AgentInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 代理信息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 提交用户id
     */
    private Long userId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String certId;

    /**
     * 校长姓名
     */
    private String principalName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 身份证号码
     */
    private String cardId;

    /**
     * 代理区域
     */
    private String agentArea;

    /**
     * 机构运营情况(每期培训学生人数)
     */
    private String perTrainNumber;

    /**
     * 机构运营具体地址
     */
    private String agentAddress;

    /**
     * 代理状态 1：审核中 2：审核完成
     */
    private Integer status;

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

    public void paramCheck() {
        if (StringUtil.isBlank(this.companyName) || StringUtil.isBlank(this.certId) || StringUtil.isBlank(this.principalName)
                || StringUtil.isBlank(this.mobile) || StringUtil.isBlank(this.cardId) || StringUtil.isBlank(this.agentArea)
                || StringUtil.isBlank(this.perTrainNumber) || StringUtil.isBlank(this.agentAddress)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
    }

}
