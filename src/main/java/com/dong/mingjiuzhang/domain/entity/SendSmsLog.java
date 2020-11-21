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
 * 活动表
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_send_sms_log")
public class SendSmsLog extends Model<SendSmsLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 发送流水号
     */
    private String serialNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 计费条数
     */
    private Long fee;

    /**
     * 用户Session内容
     */
    private String sessionContext;

    /**
     * 短信请求错误码
     */
    private String code;

    /**
     * 短信请求错误码描述
     */
    private String message;

    /**
     * 是否发送成功：0否 1是
     */
    private Integer isSuccess;

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
