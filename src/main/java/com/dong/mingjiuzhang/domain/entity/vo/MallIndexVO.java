package com.dong.mingjiuzhang.domain.entity.vo;

import com.dong.mingjiuzhang.domain.entity.CommodityExchangeRecord;
import lombok.Data;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-11-23 16:28
 * @Description
 **/
@Data
public class MallIndexVO {
    /**
     * 用户当前积分
     */
    private Long integral;
    /**
     * 积分兑换记录
     */
    private List<CommodityExchangeRecord> commodityExchangeRecordList;
}
