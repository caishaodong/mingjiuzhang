package com.dong.mingjiuzhang.controller.common;


import com.dong.mingjiuzhang.domain.entity.SysCity;
import com.dong.mingjiuzhang.global.ResponseResult;
import com.dong.mingjiuzhang.global.base.BaseController;
import com.dong.mingjiuzhang.service.SysCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author caishaodong
 * @Date 2020-11-20 23:12
 * @Description (公共)中国省市区地区表
 **/
@RestController
@RequestMapping("anonymous/sysCity")
public class SysCityController extends BaseController {
    @Autowired
    private SysCityService sysCityService;

    /**
     * 根据地区编码获取地区名称
     *
     * @param code 地区编码
     * @return
     */
    @GetMapping("/getNameByAreaCode/{code}")
    public ResponseResult<String> getNameByAreaCode(@PathVariable(value = "code") Integer code) {
        String name = sysCityService.getNameByAreaCode(code);
        return success(name);
    }

    /**
     * 根据父级城市编码获取子级城市数据（获取省份时，parentCode传1）
     *
     * @param parentCode 父级地区编码
     * @return
     */
    @GetMapping("/getCityListByParentCode/{parentCode}")
    public ResponseResult<List<SysCity>> getCityListByParentCode(@PathVariable(value = "parentCode") Integer parentCode) {
        List<SysCity> cityList = sysCityService.getCityListByParentCode(parentCode);
        return success(cityList);
    }

}
