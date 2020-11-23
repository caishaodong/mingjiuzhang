package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.Commodity;
import com.dong.mingjiuzhang.domain.entity.CommodityExchangeRecord;
import com.dong.mingjiuzhang.domain.entity.dto.CommoditySearchDTO;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.enums.YesNoEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.global.util.reflect.ReflectUtil;
import com.dong.mingjiuzhang.mapper.CommodityMapper;
import com.dong.mingjiuzhang.service.CommodityService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private UserService userService;

    @Override
    public Commodity getOkById(Long id) {
        return this.getOne(new LambdaQueryWrapper<Commodity>().eq(Commodity::getId, id).eq(Commodity::getIsDeleted, YesNoEnum.NO.getValue()));
    }

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

    /**
     * 兑换商品
     *
     * @param userId
     * @param commodityId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exchangeCommodity(Long userId, Long commodityId) {
        Commodity commodity = this.getOkById(commodityId);
        // 商品已兑换数量加一
        int records = this.baseMapper.exchangeCommodity(commodityId);
        if (records <= 0) {
            throw new BusinessException(BusinessEnum.COMMODITY_NOT_ENOUGH);
        }
        // 增加兑换记录
        CommodityExchangeRecord commodityExchangeRecord = new CommodityExchangeRecord();
        commodityExchangeRecord.setUserId(userId);
        commodityExchangeRecord.setCommodityId(commodityId);
        commodityExchangeRecord.setCommodityName(commodity.getCommodityName());
        commodityExchangeRecord.setCommodityImage(commodity.getCommodityImage());
        commodityExchangeRecord.setIntegral(commodity.getCommodityIntegral());
        commodityExchangeRecord.setStatus(YesNoEnum.NO.getValue());
        ReflectUtil.setCreateInfo(commodityExchangeRecord, CommodityExchangeRecord.class);
        // 减去用户积分
        userService.subtractIntegral(userId, commodity.getCommodityIntegral());
    }
}
