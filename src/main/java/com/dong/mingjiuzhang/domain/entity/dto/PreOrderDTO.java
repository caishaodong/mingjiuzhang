package com.dong.mingjiuzhang.domain.entity.dto;

import com.dong.mingjiuzhang.domain.entity.Order;
import com.dong.mingjiuzhang.domain.entity.User;
import lombok.Data;

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
}
