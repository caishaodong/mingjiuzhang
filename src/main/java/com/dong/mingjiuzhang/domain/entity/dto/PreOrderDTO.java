package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.domain.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author caishaodong
 * @Date 2020-11-25 19:58
 * @Description
 **/
@Data
public class PreOrderDTO extends Order {
    /**
     * 待拼团订单
     */
    private Order groupOrder;
    /**
     * 拼团参与人
     */
    private User slaveUser;
    /**
     * 拼团用户订单有效期
     */
    private LocalDateTime gmtGroupOrderExpiration;
}
