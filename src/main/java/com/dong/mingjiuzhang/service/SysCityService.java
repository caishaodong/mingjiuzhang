package com.dong.mingjiuzhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.mingjiuzhang.domain.entity.SysCity;
import com.dong.mingjiuzhang.domain.entity.vo.SysCityVO;

import java.util.List;

/**
 * <p>
 * 中国省市区地名表 服务类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-21
 */
public interface SysCityService extends IService<SysCity> {
    /**
     * 根据地区编码获取地区名称
     *
     * @param code
     * @return
     */
    String getNameByAreaCode(Integer code);

    /**
     * 根据父级城市编码获取子级城市数据
     *
     * @param parentCode
     * @return
     */
    List<SysCity> getCityListByParentCode(Integer parentCode);

    /**
     * 根据编码获取城市
     *
     * @param code
     * @return
     */
    SysCity getSysCityByCode(Integer code);

    /**
     * 根据区县编码获取城市信息
     *
     * @param areaCode
     * @return
     */
    SysCityVO getSysCityByAreaCode(Integer areaCode);

}
