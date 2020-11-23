package com.dong.mingjiuzhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.mingjiuzhang.domain.entity.Commodity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 兑换商品
     *
     * @param commodityId
     * @return
     */
    int exchangeCommodity(@Param("commodityId") Long commodityId);
}
