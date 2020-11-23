package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.CommodityExchangeRecord;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.CommodityExchangeRecordMapper;
import com.dong.mingjiuzhang.service.CommodityExchangeRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品兑换记录 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class CommodityExchangeRecordServiceImpl extends ServiceImpl<CommodityExchangeRecordMapper, CommodityExchangeRecord> implements CommodityExchangeRecordService {

    /**
     * 获取商品兑换记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<CommodityExchangeRecord> list(Long userId) {
        return this.list(new LambdaQueryWrapper<CommodityExchangeRecord>().eq(CommodityExchangeRecord::getUserId, userId)
                .eq(CommodityExchangeRecord::getIsDeleted, YesNoEnum.NO.getValue()));
    }
}
