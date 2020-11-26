package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 拼团人数加1
     *
     * @param orderId
     * @return
     */
    int incrCurrentGroupCount(@Param("orderId") Long orderId);
}
