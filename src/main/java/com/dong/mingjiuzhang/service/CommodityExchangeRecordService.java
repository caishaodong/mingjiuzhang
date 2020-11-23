package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.CommodityExchangeRecord;

import java.util.List;

/**
 * <p>
 * 商品兑换记录 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface CommodityExchangeRecordService extends IService<CommodityExchangeRecord> {

    /**
     * 获取商品兑换记录
     *
     * @param userId
     * @return
     */
    List<CommodityExchangeRecord> list(Long userId);
}
