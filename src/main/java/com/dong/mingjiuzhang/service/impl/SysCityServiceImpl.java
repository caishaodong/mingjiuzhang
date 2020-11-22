package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SysCity;
import com.dong.mingjiuzhang.domain.entity.vo.SysCityVO;
import com.dong.mingjiuzhang.mapper.SysCityMapper;
import com.dong.mingjiuzhang.service.SysCityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 中国省市区地名表 服务实现类
 * </p>
 *
 * @author caishaodong
 * @since 2020-11-21
 */
@Service
public class SysCityServiceImpl extends ServiceImpl<SysCityMapper, SysCity> implements SysCityService {
    /**
     * 根据地区编码获取地区名称
     *
     * @param code
     * @return
     */
    @Override
    public String getNameByAreaCode(Integer code) {
        String name = "";
        SysCity sysCity = this.baseMapper.selectOne(new LambdaQueryWrapper<SysCity>().eq(SysCity::getCode, code));
        Integer parentCode;
        if (Objects.nonNull(sysCity) && Objects.nonNull(parentCode = sysCity.getParentCode())) {
            name = sysCity.getName();
            SysCity parentCity = this.baseMapper.selectOne(new LambdaQueryWrapper<SysCity>().eq(SysCity::getCode, parentCode));
            Integer pParentCode;
            if (Objects.nonNull(parentCity) && Objects.nonNull(pParentCode = parentCity.getParentCode())) {
                name = parentCity.getName() + name;
                SysCity pParentCity = this.baseMapper.selectOne(new LambdaQueryWrapper<SysCity>().eq(SysCity::getCode, pParentCode));
                if (Objects.nonNull(pParentCity)) {
                    name = pParentCity.getName() + name;
                }
            }
        }
        return name;
    }

    /**
     * 根据父级城市编码获取子级城市数据
     *
     * @param parentCode
     * @return
     */
    @Override
    public List<SysCity> getCityListByParentCode(Integer parentCode) {
        parentCode = Objects.isNull(parentCode) ? 1 : parentCode;
        List<SysCity> cityList = this.baseMapper.selectList(new LambdaQueryWrapper<SysCity>().eq(SysCity::getParentCode, parentCode));
        return cityList;
    }

    /**
     * 根据编码获取城市
     *
     * @param code
     * @return
     */
    @Override
    public SysCity getSysCityByCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        SysCity sysCity = this.getOne(new LambdaQueryWrapper<SysCity>().eq(SysCity::getCode, code));
        return sysCity;
    }

    /**
     * 根据区县编码获取城市信息
     *
     * @param areaCode
     * @return
     */
    @Override
    public SysCityVO getSysCityByAreaCode(Integer areaCode) {
        if (Objects.isNull(areaCode)) {
            return null;
        }
        // 获取区县
        SysCity area = this.getSysCityByCode(areaCode);
        if (Objects.isNull(area)) {
            return null;
        }
        // 获取城市
        SysCity city = this.getSysCityByCode(area.getParentCode());
        if (Objects.isNull(city)) {
            return null;
        }
        // 获取省
        SysCity province = this.getSysCityByCode(city.getParentCode());
        if (Objects.isNull(province)) {
            return null;
        }
        SysCityVO sysCityVO = new SysCityVO();
        sysCityVO.setProvinceCode(province.getCode());
        sysCityVO.setProvince(province.getName());
        sysCityVO.setCityCode(city.getCode());
        sysCityVO.setCity(city.getName());
        sysCityVO.setAreaCode(area.getCode());
        sysCityVO.setArea(area.getName());
        return sysCityVO;
    }
}
