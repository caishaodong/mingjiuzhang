package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.Commodity;
import com.dong.mingjiuzhang.domain.entity.dto.CommoditySearchDTO;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
public interface CommodityService extends IService<Commodity> {
    Commodity getOkById(Long id);

    /**
     * 获取商品列表（分页）
     *
     * @param commoditySearchDTO
     * @return
     */
    IPage<Commodity> pageList(CommoditySearchDTO commoditySearchDTO);

    /**
     * 兑换商品
     *
     * @param userId
     * @param commodityId
     */
    void exchangeCommodity(Long userId, Long commodityId);
}
