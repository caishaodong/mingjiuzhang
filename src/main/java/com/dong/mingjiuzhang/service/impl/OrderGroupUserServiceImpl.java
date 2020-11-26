package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.OrderGroupUser;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.mapper.OrderGroupUserMapper;
import com.dong.mingjiuzhang.service.OrderGroupUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 拼团订单用户表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-24
 */
@Service
public class OrderGroupUserServiceImpl extends ServiceImpl<OrderGroupUserMapper, OrderGroupUser> implements OrderGroupUserService {

    /**
     * 获取当前拼团的用户信息
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderGroupUser> getByOrderId(Long orderId) {
        List<OrderGroupUser> orderGroupUsers = this.baseMapper.selectList(new LambdaQueryWrapper<OrderGroupUser>().eq(OrderGroupUser::getOrderId, orderId)
                .eq(OrderGroupUser::getIsDeleted, YesNoEnum.NO.getValue()));
        if (CollectionUtils.isEmpty(orderGroupUsers)) {
            throw new BusinessException(BusinessEnum.DATA_ERROR);
        }
        return orderGroupUsers;
    }

    /**
     * 根据团购订单编号获取团购订单
     *
     * @param orderId
     * @param groupOrderSn
     * @return
     */
    @Override
    public OrderGroupUser getByOrderIdAndGroupOrderSn(Long orderId, String groupOrderSn) {
        return this.getOne(new LambdaQueryWrapper<OrderGroupUser>().eq(OrderGroupUser::getOrderId, orderId)
                .eq(OrderGroupUser::getGroupOrderSn, groupOrderSn)
                .eq(OrderGroupUser::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
