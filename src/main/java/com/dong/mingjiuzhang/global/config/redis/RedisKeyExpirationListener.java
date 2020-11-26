package com.dong.mingjiuzhang.global.config.redis;

import com.dong.mingjiuzhang.global.constant.OrderConstants;
import com.dong.mingjiuzhang.global.util.pay.OrderPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @Author caishaodong
 * @Date 2020-11-26 19:50
 * @Description
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    @Autowired
    private OrderPayService orderPayService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        LOGGER.info("RedisKeyExpirationListener 监听 redis key 失效; key = {}", expiredKey);
        if (expiredKey.startsWith(OrderConstants.ORDER_PREFIX)) {
            // 订单超时处理
            String orderSn = expiredKey;
            // 将待支付的订单改为已取消(超时未支付)
            orderPayService.orderPayTimeout(orderSn);
        } else if (expiredKey.startsWith(OrderConstants.GROUP_ORDER_PREFIX)) {
            // 拼团订单超时处理
            String groupOrderSn = expiredKey;
            // 将待支付的订单改为已取消(超时未支付)
            orderPayService.groupOrderPayTimeout(groupOrderSn);
        }
    }
}
