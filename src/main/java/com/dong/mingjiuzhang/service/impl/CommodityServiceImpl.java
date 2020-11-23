package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Commodity;
import com.dong.mingjiuzhang.domain.entity.dto.CommoditySearchDTO;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.mapper.CommodityMapper;
import com.dong.mingjiuzhang.service.CommodityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    /**
     * 获取商品列表（分页）
     *
     * @param commoditySearchDTO
     * @return
     */
    @Override
    public IPage<Commodity> pageList(CommoditySearchDTO commoditySearchDTO) {
        return this.page(commoditySearchDTO, new LambdaQueryWrapper<Commodity>().eq(Commodity::getIsDeleted, YesNoEnum.NO.getValue())
                .orderByAsc(Commodity::getSort).orderByAsc(Commodity::getId));
    }
}
