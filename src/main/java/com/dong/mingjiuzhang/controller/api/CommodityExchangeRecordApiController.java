package com.dong.mingjiuzhang.controller.api;


import com.dong.mingjiuzhang.domain.entity.Commodity;
import com.dong.mingjiuzhang.domain.entity.CommodityExchangeRecord;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.enums.BusinessEnum;
import com.dong.mingjiuzhang.global.exception.BusinessException;
import com.dong.mingjiuzhang.service.CommodityExchangeRecordService;
import com.dong.mingjiuzhang.service.CommodityService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * (API)商品兑换记录
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/commodityExchangeRecord")
public class CommodityExchangeRecordApiController extends BaseController {
    @Autowired
    private CommodityExchangeRecordService commodityExchangeRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;

    /**
     * 获取商品兑换记录
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<List<CommodityExchangeRecord>> list(HttpServletRequest request) {
        User user = userService.getUserByToken(request);
        List<CommodityExchangeRecord> list = commodityExchangeRecordService.list(user.getId());
        return success(list);
    }

    /**
     * 兑换商品
     *
     * @param request
     * @param commodityId 商品id
     * @return
     */
    @PostMapping("/exchangeCommodity/{commodityId}")
    public ResponseResult exchangeCommodity(HttpServletRequest request, @PathVariable("commodityId") Long commodityId) {
        User user = userService.getUserByToken(request);
        // 校验商品是否存在
        Commodity commodity = commodityService.getOkById(commodityId);
        if (Objects.isNull(commodity)) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }
        // 校验商品数量是否足够
        Long commodityRemainNumber = commodity.getCommodityRemainNumber();
        if (commodityRemainNumber.longValue() <= 0) {
            throw new BusinessException(BusinessEnum.COMMODITY_NOT_ENOUGH);
        }
        // 校验用户积分是否足够
        Long commodityIntegral = commodity.getCommodityIntegral();
        Long integral = user.getIntegral();
        if (integral.longValue() < commodityIntegral.longValue()) {
            throw new BusinessException(BusinessEnum.USER_COMMODITY_NOT_ENOUGH);
        }
        // 兑换商品
        commodityService.exchangeCommodity(user.getId(), commodityId);
        return success();
    }

}
