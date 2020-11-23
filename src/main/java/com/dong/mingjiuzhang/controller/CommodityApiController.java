package com.dong.mingjiuzhang.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dong.mingjiuzhang.domain.entity.Commodity;
import com.dong.mingjiuzhang.domain.entity.dto.CommoditySearchDTO;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.global.util.page.PageUtil;
import com.dong.mingjiuzhang.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (API)商品
 *
 * @author caishaodong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/api/commodity")
public class CommodityApiController extends BaseController {
    @Autowired
    private CommodityService commodityService;

    /**
     * 获取商品列表（分页）
     *
     * @param commoditySearchDTO
     * @return
     */
    public ResponseResult<PageUtil<Commodity>> pageList(@RequestBody CommoditySearchDTO commoditySearchDTO) {
        IPage<Commodity> page = commodityService.pageList(commoditySearchDTO);
        return success(page);
    }
}
