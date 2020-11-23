package com.dong.mingjiuzhang.controller;


import com.dong.mingjiuzhang.domain.entity.CommodityExchangeRecord;
import com.dong.mingjiuzhang.domain.entity.User;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.CommodityExchangeRecordService;
import com.dong.mingjiuzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

}
