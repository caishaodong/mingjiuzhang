package com.dong.mingjiuzhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.mingjiuzhang.domain.entity.SysCity;
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
}
